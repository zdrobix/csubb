#include <iostream>
#include <fstream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <queue>
#include <vector>
#include <chrono>
#include <random>
#include <atomic>
#include <string>
#include <functional>
#include <algorithm>

using namespace std;
using namespace chrono;

class ThreadPool {
    vector<thread> workers;
    queue<function<void()>> tasks;
    mutex queueMutex;
    condition_variable condition;
    bool stop;

public:
    ThreadPool(size_t threads) : stop(false) {
        for (size_t i = 0; i < threads; ++i) {
            workers.emplace_back([this] {
                while (true) {
                    function<void()> task;
                    {
                        unique_lock<mutex> lock(this->queueMutex);
                        this->condition.wait(lock, [this] {
                            return this->stop || !this->tasks.empty();
                            });
                        if (this->stop && this->tasks.empty())
                            return;
                        task = move(this->tasks.front());
                        this->tasks.pop();
                    }
                    task();
                }
                });
        }
    }

    template<class F>
    void enqueue(F&& f) {
        {
            unique_lock<mutex> lock(queueMutex);
            tasks.emplace(forward<F>(f));
        }
        condition.notify_one();
    }

    ~ThreadPool() {
        {
            unique_lock<mutex> lock(queueMutex);
            stop = true;
        }
        condition.notify_all();
        for (thread& worker : workers)
            worker.join();
    }
};

struct Node {
    int id;
    int nota;
    Node* next;
    mutex nodeMutex;

    Node(int i, int n) : id(i), nota(n), next(nullptr) {}
    Node() : id(-1), nota(0), next(nullptr) {} 
};

class FineGrainedList {
    Node* head;  
    Node* tail;  

public:
    FineGrainedList() {
        head = new Node();
        tail = new Node();
        head->id = INT32_MIN;
        tail->id = INT32_MAX;
        head->next = tail;
    }

    void add(int id, int nota) {
        head->nodeMutex.lock();
        Node* pred = head;
        Node* curr = pred->next;
        curr->nodeMutex.lock();

        while (curr->id < id) {
            pred->nodeMutex.unlock();
            pred = curr;
            curr = curr->next;
            curr->nodeMutex.lock();
        }

        if (curr->id == id) {
            curr->nota += nota;
            pred->nodeMutex.unlock();
            curr->nodeMutex.unlock();
        }
        else {
            Node* newNode = new Node(id, nota);
            newNode->next = curr;
            pred->next = newNode;
            pred->nodeMutex.unlock();
            curr->nodeMutex.unlock();
        }
    }

    void insertSorted(int id, int nota) {
        head->nodeMutex.lock();
        Node* pred = head;
        Node* curr = pred->next;
        curr->nodeMutex.lock();

        while (curr->id != INT32_MAX && curr->nota > nota) {
            pred->nodeMutex.unlock();
            pred = curr;
            curr = curr->next;
            curr->nodeMutex.lock();
        }

        Node* newNode = new Node(id, nota);
        newNode->next = curr;
        pred->next = newNode;
        pred->nodeMutex.unlock();
        curr->nodeMutex.unlock();
    }

    vector<pair<int, int>> getAllNodes() {
        vector<pair<int, int>> result;
        Node* curr = head->next;
        while (curr != tail) {
            result.push_back({ curr->id, curr->nota });
            curr = curr->next;
        }
        return result;
    }

    void saveToFile(const string& filename) {
        ofstream out(filename);
        Node* curr = head->next;
        while (curr != tail) {
            out << curr->id << " " << curr->nota << "\n";
            curr = curr->next;
        }
        out.close();
    }

    ~FineGrainedList() {
        Node* curr = head;
        while (curr) {
            Node* tmp = curr;
            curr = curr->next;
            delete tmp;
        }
    }
};

class BoundedQueue {
    queue<pair<int, int>> q;
    mutex mtx;
    condition_variable notFull;
    condition_variable notEmpty;
    size_t capacity;
    atomic<bool> readingComplete{ false };

public:
    atomic<int> activeReaders{ 0 };
    BoundedQueue(size_t cap) : capacity(cap) {}

    void push(int id, int nota) {
        unique_lock<mutex> lock(mtx);
        notFull.wait(lock, [this] { return q.size() < capacity; });
        q.push({ id, nota });
        notEmpty.notify_one();
    }

    bool pop(int& id, int& nota) {
        unique_lock<mutex> lock(mtx);
        notEmpty.wait(lock, [this] {
            return !q.empty() || (activeReaders == 0 && readingComplete);
            });

        if (q.empty() && activeReaders == 0 && readingComplete) {
            notEmpty.notify_all(); 
            return false;
        }

        auto p = q.front();
        q.pop();
        id = p.first;
        nota = p.second;
        notFull.notify_one();
        return true;
    }

    void setReadingComplete() {
        readingComplete = true;
        notEmpty.notify_all();
    }
};

void generateFiles(int numStudents, int numProjects) {
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> notaDist(1, 10);
    uniform_int_distribution<> studentDist(1, numStudents);

    for (int proj = 1; proj <= numProjects; proj++) {
        ofstream out("proiect" + to_string(proj) + ".txt");
        int numNotes = 80 + rand() % 41;
        for (int i = 0; i < numNotes; i++) {
            out << studentDist(gen) << " " << notaDist(gen) << "\n";
        }
        out.close();
    }
}

void readerTask(int projectNum, BoundedQueue& q) {
    ifstream in("proiect" + to_string(projectNum) + ".txt");
    int id, nota;
    while (in >> id >> nota) {
        q.push(id, nota);
    }
    in.close();
    q.activeReaders--;
}

void workerThread(BoundedQueue& q, FineGrainedList& lista) {
    int id, nota;
    while (q.pop(id, nota)) {
        lista.add(id, nota);
    }
}

void sortingWorker(vector<pair<int, int>>& nodes,
    atomic<size_t>& index,
    FineGrainedList& sortedList) {
    while (true) {
        size_t i = index.fetch_add(1);
        if (i >= nodes.size()) break;
        sortedList.insertSorted(nodes[i].first, nodes[i].second);
    }
}

void parallelMethod(int p_r, int p_w, int maxQueueSize,
    FineGrainedList& lista, FineGrainedList& sortedList) {
    BoundedQueue q(maxQueueSize);
    q.activeReaders = 10; 

    ThreadPool pool(p_r);

    for (int i = 1; i <= 10; i++) {
        pool.enqueue([i, &q]() {
            readerTask(i, q);
            });
    }

    vector<thread> workers;
    for (int i = 0; i < p_w; i++) {
        workers.push_back(thread(workerThread, ref(q), ref(lista)));
    }

    pool.~ThreadPool();

    q.setReadingComplete();

    for (auto& t : workers) {
        t.join();
    }

    auto nodes = lista.getAllNodes();
    atomic<size_t> index{ 0 };

    vector<thread> sorters;
    for (int i = 0; i < p_w; i++) {
        sorters.push_back(thread(sortingWorker, ref(nodes), ref(index), ref(sortedList)));
    }

    for (auto& t : sorters) {
        t.join();
    }
}

int main() {
    cout << "Generating test files...\n";
    generateFiles(500, 10);

    cout << "\n# Laborator 5 - Rezultate procesare\n\n";
    cout << "## Parametri:\n";
    cout << "- Numar studenti: 500\n";
    cout << "- p_r (readers): 4\n";
    cout << "- Capacitate coada: variabila\n\n";

    cout << "## Rezultate\n";
    cout << "|p_w |MAX Queue |Timp (ms) |\n";
    cout << "|-|-|-|\n";

    int p_r = 4;
    int workers[] = { 2, 4, 8 };
    int queueSizes[] = { 50, 100 };

    for (int maxQueue : queueSizes) {
        for (int p_w : workers) {
            double sumTime = 0;

            for (int run = 0; run < 9; run++) {
                FineGrainedList lista;
                FineGrainedList sortedList;

                auto start = high_resolution_clock::now();
                parallelMethod(p_r, p_w, maxQueue, lista, sortedList);
                sortedList.saveToFile("rezultate_sorted.txt");
                auto end = high_resolution_clock::now();

                sumTime += duration<double, milli>(end - start).count();
            }

            cout << "| " << p_w << " | " << maxQueue
                << " | " << sumTime / 10 << " |\n";
        }
    }

    cout << "\nRezultatele sortate au fost salvate in 'rezultate_sorted.txt'\n";

    return 0;
}