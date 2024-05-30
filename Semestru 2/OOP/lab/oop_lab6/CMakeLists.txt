cmake_minimum_required(VERSION 3.27)
project(oop_lab6)

set(CMAKE_CXX_STANDARD 14)

add_executable(oop_lab6
        main.cpp
        ui.cpp
        ui.h
        repo.cpp
        repo.h
        service.cpp
        service.h
        domain.cpp
        domain.h
        tests.cpp
        tests.h
        vector.h
)
