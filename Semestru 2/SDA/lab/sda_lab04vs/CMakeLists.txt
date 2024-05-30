cmake_minimum_required(VERSION 3.27)
project(sda_lab04vs)

set(CMAKE_CXX_STANDARD 14)

include_directories(lab04sda/lab04sda)

add_executable(sda_lab04vs
        lab04sda/lab04sda/App.cpp
        lab04sda/lab04sda/DO.cpp
        lab04sda/lab04sda/DO.h
        lab04sda/lab04sda/Iterator.cpp
        lab04sda/lab04sda/Iterator.h
        lab04sda/lab04sda/TestExtins.cpp
        lab04sda/lab04sda/TestExtins.h
        lab04sda/lab04sda/TestScurt.cpp
        lab04sda/lab04sda/TestScurt.h)
