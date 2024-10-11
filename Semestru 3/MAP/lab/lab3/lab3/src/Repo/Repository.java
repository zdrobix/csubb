package Repo;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>>{

    Entity find(ID id);

    Iterable findAll();

    Entity save(Entity e);

    void delete(ID id);
}
