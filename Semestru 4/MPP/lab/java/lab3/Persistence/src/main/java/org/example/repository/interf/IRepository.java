package org.example.repository.interf;

import org.example.domain.Entity;

public interface IRepository<ID, E extends Entity<ID>> {
    E FindById (ID id);
    Iterable<E> FindAll();
    E Save (E entity);
    E Update (E entity);
    E Delete (ID id);
}
