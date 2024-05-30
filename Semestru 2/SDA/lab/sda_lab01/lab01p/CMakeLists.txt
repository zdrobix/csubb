cmake_minimum_required(VERSION 3.27)
project(lab01p)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(lab01p
        App.cpp
        IteratorVectorDinamic.cpp
        IteratorVectorDinamic.h
        VectorDinamic.cpp
        VectorDinamic.h
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)

