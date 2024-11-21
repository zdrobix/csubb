package com.example.demo.domain;

public class Tema extends Entity<String>{

    private String desc;

    public Tema (String desc_, String id_) {
        this.desc = desc_;
        super.setId(id_);
    }

    public String getDesc() {
        return this.desc;
    }

    public String toString() {
        return this.desc;
    }
}
