package Repo;

import java.util.*;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E>{

    private Map<ID, Entity> entities;

    public InMemoryRepository() {
        this.entities = new HashMap<>();
    }

    @Override
    public Entity find(Object o) {
        for (Entity e : this.entities.values()) {
            if (e.equals(o))
                return e;
        }
        return null;
    }

    @Override
    public Iterable findAll() {
        return entities.values();
    }

    @Override
    public Entity save(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        Entity aux = this.entities.get(entity.getID());
        if ( aux == null) {
            this.entities.put((ID)entity.getID(), entity);
            return null;
        }
        return aux;
    }

    @Override
    public void delete(Object o) {

    }
}
