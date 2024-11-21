package com.example.demo.domain;

public class Student extends Entity<Integer>{
    private String name;
    private int group;

    public Student (int id_, String name_, int group_) {
        this.group = group_;
        this.name = name_;
        super.setId(id_);
    }

    public String getName() {
        return this.name;
    }

    public int getGroup() {
        return this.group;
    }

    public String toString() {
        return this.name + " " + this.group;
    }
}
