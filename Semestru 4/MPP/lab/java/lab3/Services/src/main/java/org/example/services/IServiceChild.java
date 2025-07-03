package org.example.services;

import org.example.domain.Child;

public interface IServiceChild {
    Iterable<Child> GetAll() ;
    Child GetById(int id);
    Child AddChild (String name, String cnp) ;
    Child UpdateChild (Child child) ;
    Iterable<Child> GetByAge (int age) ;
}
