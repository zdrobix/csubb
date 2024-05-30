cmake_minimum_required(VERSION 3.27)
project(sda_lab05)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(sda_lab05
        App.cpp
        Matrice.cpp
        Matrice.h
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h
)
