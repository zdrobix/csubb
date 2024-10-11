package domain;

import java.util.Objects;

public class Student implements Comparable<Student> {
    private String nume;
    private float media;

    public Student(String nume_, float media_) {
        this.nume = nume_;
        this.media = media_;
    }

    public String getName() {
        return this.nume;
    }

    public float getMedia() {
        return this.media;
    }

    @Override
    public String toString() {
        return "" + nume + " " + media;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof Student))
            return false;
        Student other = (Student) obj;
        return other.media == media && other.nume.equals(nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nume, this.media);
    }

    public int compareTo(Student student) {
        return this.nume.compareTo(student.nume);
    }


}
