package com.example.demo.domain;

public class Entity<ID>{
    private ID id;

    public ID getId() {
        return this.id;
    }
    public void setId(ID id_) {
        this.id = id_;
    }
}
