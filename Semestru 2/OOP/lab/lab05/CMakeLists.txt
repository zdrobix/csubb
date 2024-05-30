cmake_minimum_required(VERSION 3.27)
project(lab05 C)

set(CMAKE_C_STANDARD 11)

add_executable(lab05 main.c
                actiuni.c
                actiuni.h
                lista.c
                lista.h
                validator.c
                validator.h
                teste.c
                teste.h
        cheltuiala.c)
