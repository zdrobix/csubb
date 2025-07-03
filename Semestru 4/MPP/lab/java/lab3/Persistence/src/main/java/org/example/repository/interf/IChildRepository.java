package org.example.repository.interf;

import org.example.domain.Child;

public interface IChildRepository extends IRepository<Integer, Child> {

    Iterable<Child> GetAllByAge(int age);
}
