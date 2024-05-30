cmake_minimum_required(VERSION 3.27)
project(oop_lab8_9)

set(CMAKE_CXX_STANDARD 14)

add_executable(oop_lab8_9
        main.cpp
        domain.cpp
        domain.h
        service.cpp
        service.h
        repo.cpp
        repo.h
        tests.cpp
        tests.h
        validator.h
        vector.h
        ui.cpp
        ui.h
        exception.h
        undo.h
)
