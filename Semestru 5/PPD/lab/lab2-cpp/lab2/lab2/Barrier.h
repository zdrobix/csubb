#pragma once
#include <mutex>
#include <condition_variable>

class SimpleBarrier {
    std::mutex mtx;
    std::condition_variable cv;
    int count;
    int num_threads;

public:
    SimpleBarrier(int n) : count(0), num_threads(n) {}

    void arrive_and_wait() {
        std::unique_lock<std::mutex> lock(mtx);
        count++;
        if (count == num_threads) {
            count = 0;  
            cv.notify_all();
        }
        else {
            cv.wait(lock, [this]() { return count == 0; });
        }
    }
};
