cmake_minimum_required(VERSION 3.27)
project(ooplab2_4_dinamic)

set(CMAKE_CXX_STANDARD 17)

set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")

add_executable(ooplab2_4_dinamic main.c
        service.c
        service.h
        repo.c
        repo.h
        cheltuiala.c
        cheltuiala.h
        teste.c
        teste.h)
