package repo.memory;

import domain.Entity;
import domain.validators.Validator;
import domain.validators.ValidationException;
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
    public E findOne(ID id) {
        if (id == null)
            throw new ValidationException("Cannot find a null id.");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        if (!entities.isEmpty())
            return entities.values();
        return null;
    }

    @Override
    public E save(E entity)  {
        if (entity == null)
            throw new ValidationException("Entity cannot be null when saving");
        this.validator.validate(entity);
        return entities.putIfAbsent(entity.getId(), entity);
    }

    @Override
    public E delete(ID id) {
        if (id == null)
            throw new ValidationException("Entity cannot be null when deleting");
        return entities.remove(id);
    }

    @Override
    public E update(E entity) {
        if (entity == null)
            throw new ValidationException("Entity cannot be null when updating");
        this.validator.validate(entity);
        this.entities.put(entity.getId(), entity);
        if (entities.get(entity.getId()) == null) {
            return entity;
        }
        this.entities.put(entity.getId(), entity);
        return null;
    }
}
