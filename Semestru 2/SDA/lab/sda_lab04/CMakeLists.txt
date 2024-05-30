cmake_minimum_required(VERSION 3.27)
project(sda_lab04)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(sda_lab04
        App.cpp
        DO.cpp
        DO.h
        Iterator.cpp
        Iterator.h
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)
