#ifndef OOP_LAB8_9_EXCEPTION_H
#define OOP_LAB8_9_EXCEPTION_H

#include <iostream>
#include <exception>
#include <string>

using namespace std;

class MyException: public exception {

private:
    string message;

public:
    explicit MyException(string& msg): message{msg} {};

    string getMessage () const {

        return message;
    }

};

#endif //OOP_LAB8_9_EXCEPTION_H
