package repo.memory;

import domain.Entity;
import domain.validators.Validator;
import domain.validators.ValidationException;
import repo.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private final Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities= new HashMap<>();
    }

    @Override
    public Optional<E> findOne(ID id) {
        if (id == null)
            throw new ValidationException("Cannot find a null id.");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll() {
        if (!entities.isEmpty())
            return entities.values();
        return null;
    }

    public Integer size () {
        int count = 0;
        for (var _ : entities.values())
            count ++;
        return count;
    }

    @Override
    public Optional<E> save(E entity)  {
        if (entity == null)
            throw new ValidationException("Entity cannot be null when saving");
        this.validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        if (id == null)
            throw new ValidationException("Entity cannot be null when deleting");
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) {
        if (entity == null)
            throw new ValidationException("Entity cannot be null when updating");
        this.validator.validate(entity);
        this.entities.put(entity.getId(), entity);
        if (entities.get(entity.getId()) == null) {
            return Optional.ofNullable(entity);
        }
        this.entities.put(entity.getId(), entity);
        return null;
    }
}
