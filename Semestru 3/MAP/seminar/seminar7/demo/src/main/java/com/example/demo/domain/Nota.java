package com.example.demo.domain;

import java.time.LocalDate;

public class Nota {
    private Student student;
    private Tema tema;
    private double nota;
    private LocalDate date;
    private String profesor;

    public Nota(Student student, Tema tema, double nota, LocalDate now, String profesor) {
        this.student = student;
        this.tema = tema;
        this.nota = nota;
        this.date = now;
        this.profesor = profesor;
    }

    public Student getStudent() {
        return student;
    }

    public Tema getTema() {
        return tema;
    }

    public double getNota() {
        return nota;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getProfesor() {
        return profesor;
    }

    public String toString() {
        return student + " " + tema + " " + nota + " " + date + " " + profesor;
    }
}