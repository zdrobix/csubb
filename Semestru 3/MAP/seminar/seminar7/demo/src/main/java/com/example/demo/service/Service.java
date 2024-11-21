package com.example.demo.service;

import com.example.demo.domain.Nota;
import com.example.demo.domain.Student;
import com.example.demo.domain.Tema;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Service {
    private List<Student> students;
    private List<Nota> note;
    private List<Tema> teme;

    public Service() {
        this.populate();
    }

    public void populate() {
        students = new ArrayList<>();
        note = new ArrayList<>();
        teme = new ArrayList<>();

        Student s1 =new Student(1, "Alex Zdroba", 227);
        Student s2 =new Student(2, "Marius George", 227);
        Student s3 =new Student(3, "Paul Ioan", 226);
        Student s4 =new Student(4, "Gavril Popescu", 225);
        Student s5 =new Student(5, "Mihalea Grindeanu", 225);
        Student s6 =new Student(6, "Paula Popoviciu-Ghermanescu", 224);

        Tema t1 = new Tema("Mate", "tema1");
        Tema t2 = new Tema("Romana", "tema1");
        Tema t3 = new Tema("Maghiara", "tema1");
        Tema t4 = new Tema("Fizica", "tema1");
        Tema t5 = new Tema("Mate", "tema2");
        Tema t6 = new Tema("Maghiara", "tema2");
        Tema t7 = new Tema("Romana", "tema2");
        Tema t8 = new Tema("Fizica", "tema2");

        Nota n1 = new Nota(s1, t1, 10, LocalDate.now(), "Profesor Georgescu");
        Nota n2 = new Nota(s2, t2, 7, LocalDate.now(), "Profesor Marian");
        Nota n3 = new Nota(s3, t3, 3, LocalDate.now(), "Profesor Ana");
        Nota n4 = new Nota(s4, t4, 4, LocalDate.now(), "Profesor Mihai");
        Nota n5 = new Nota(s5, t5, 5, LocalDate.now(), "Profesor Axente");
        Nota n6 = new Nota(s6, t6, 9, LocalDate.now(), "Profesor Rodica");
        Nota n7 = new Nota(s1, t7, 10, LocalDate.now(), "Profesor Alex");
        Nota n8 = new Nota(s1, t7, 4, LocalDate.now(), "Profesor Da");

        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
        students.add(s6);

        note.add(n1);
        note.add(n2);
        note.add(n3);
        note.add(n4);
        note.add(n5);
        note.add(n6);
        note.add(n7);
        note.add(n8);

        teme.add(t1);
        teme.add(t2);
        teme.add(t3);
        teme.add(t4);
        teme.add(t5);
        teme.add(t6);
        teme.add(t7);
        teme.add(t8);
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<Nota> getNote() {
        return this.note;
    }

    public List<Tema> getTeme() {
        return this.teme;
    }

    public Student getStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }


    public List<Nota> getNoteFiltrareNumeStudent(Predicate<Nota> predicate) {
        List<Nota> result = new ArrayList<>();
        this.getNote().forEach(
                nota -> {
                    if(predicate.test(nota)) {
                        result.add(nota);
                    }
                }
        );
        return result;
    }

    public ObservableList getTemeUnice() {
        List<Tema> result = new ArrayList<>();
        for (Tema t : teme) {
            if (!result.contains(t)) {
                result.add(t);
            }
        }
        return (ObservableList) result;
    }
}
