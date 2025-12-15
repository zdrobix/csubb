#include <iostream>
#include <fstream>
#include <thread>
#include <mutex>
#include <queue>
#include <vector>
#include <chrono>
#include <random>
#include <atomic>
#include <string>

using namespace std;
using namespace chrono;

struct Node {
    int id;
    int nota;
    Node* next;
    Node(int i, int n) : id(i), nota(n), next(nullptr) {}
};

class ThreadSafeList {
    Node* head;
    mutex mtx;
public:
    ThreadSafeList() : head(nullptr) {}

    void add(int id, int nota) {
        lock_guard<mutex> lock(mtx);
        Node* curr = head;
        while (curr) {
            if (curr->id == id) {
                curr->nota += nota;
                return;
            }
            curr = curr->next;
        }
        Node* newNode = new Node(id, nota);
        newNode->next = head;
        head = newNode;
    }

    void saveToFile(const string& filename) {
        ofstream out(filename);
        Node* curr = head;
        while (curr) {
            out << curr->id << " " << curr->nota << "\n";
            curr = curr->next;
        }
        out.close();
    }

    ~ThreadSafeList() {
        while (head) {
            Node* tmp = head;
            head = head->next;
            delete tmp;
        }
    }
};

class ThreadSafeQueue {
    queue<pair<int, int>> q;
    mutex mtx;
public:
    atomic<int> activeReaders{ 0 };

    void push(int id, int nota) {
        lock_guard<mutex> lock(mtx);
        q.push({ id, nota });
    }

    bool pop(int& id, int& nota) {
        while (true) {
            {
                lock_guard<mutex> lock(mtx);
                if (!q.empty()) {
                    auto p = q.front();
                    q.pop();
                    id = p.first;
                    nota = p.second;
                    return true;
                }
            }
            if (activeReaders == 0) {
                lock_guard<mutex> lock(mtx);
                if (q.empty()) return false;
            }
            this_thread::sleep_for(microseconds(100));
        }
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

void sequentialMethod(ThreadSafeList& lista) {
    for (int proj = 1; proj <= 10; proj++) {
        ifstream in("proiect" + to_string(proj) + ".txt");
        int id, nota;
        while (in >> id >> nota) {
            lista.add(id, nota);
        }
        in.close();
    }
}

void readerThread(int start, int end, ThreadSafeQueue& q) {
    for (int proj = start; proj <= end; proj++) {
        ifstream in("proiect" + to_string(proj) + ".txt");
        int id, nota;
        while (in >> id >> nota) {
            q.push(id, nota);
        }
        in.close();
    }
    q.activeReaders--;
}

void workerThread(ThreadSafeQueue& q, ThreadSafeList& lista) {
    int id, nota;
    while (q.pop(id, nota)) {
        lista.add(id, nota);
    }
}

void parallelMethod(int p, int p_r, ThreadSafeList& lista) {
    ThreadSafeQueue q;
    vector<thread> threads;

    q.activeReaders = p_r;

    int filesPerReader = 10 / p_r;
    for (int i = 0; i < p_r; i++) {
        int start = i * filesPerReader + 1;
        int end = (i == p_r - 1) ? 10 : (i + 1) * filesPerReader;
        threads.push_back(thread(readerThread, start, end, ref(q)));
    }

    int p_w = p - p_r;
    for (int i = 0; i < p_w; i++) {
        threads.push_back(thread(workerThread, ref(q), ref(lista)));
    }

    for (auto& t : threads) {
        t.join();
    }
}

int main() {
    generateFiles(200, 10);

    ThreadSafeList listaSeq;
    double sumSeq = 0;
    for (int i = 0; i < 10; i++) {
        auto start = high_resolution_clock::now();
        sequentialMethod(listaSeq);
        listaSeq.saveToFile("rezultate.txt");
        auto end = high_resolution_clock::now();
        sumSeq += duration<double, milli>(end - start).count();
    }

    cout << "# Rezultate procesare\n\n";
    cout << "## Metoda secventiala\n";
    cout << "- **Durata:** " << sumSeq / 10 << " ms\n\n";

    cout << "## Metoda paralela\n";
    cout << "| p_total | p_reader | timp (ms)  |\n";
    cout << "|-|-|-|\n";

    int configs[][2] = { {4,1}, {8,1}, {16,1}, {4,2}, {8,2}, {16,2} };

    for (auto& cfg : configs) {
        int p = cfg[0], p_r = cfg[1];
        ThreadSafeList listaPar;
        double sumPar = 0;
        for (int i = 0; i < 10; i++) {

            auto start = high_resolution_clock::now();
            parallelMethod(p, p_r, listaPar);
            listaPar.saveToFile("rezultate_par.txt");
            auto end = high_resolution_clock::now();
            sumPar += duration<double, milli>(end - start).count();
        }
        cout << "| " << p
            << " | " << p_r
            << " | " << sumPar / 10 << std::endl;
    }

    return 0;
}