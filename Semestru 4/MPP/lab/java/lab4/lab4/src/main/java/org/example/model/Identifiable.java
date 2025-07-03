package org.example.model;

public interface Identifiable<Tid> {
    Tid getID();
    void setID(Tid id);
}
