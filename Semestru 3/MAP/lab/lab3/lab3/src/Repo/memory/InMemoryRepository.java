package repo.memory;

import domain.Entity;
import domain.validators.Validator;
import repo.Repository;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }


    @Override
    public Optional<E> findOne(ID id) {
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return null;

    }

    @Override
    public Optional<E> save(E entity)  {
        return null;
    }

    @Override
    public Optional<E> delete(ID id) {
        return null;
    }

    @Override
    public Optional<E> update(E entity) {
        return null;
    }

}
