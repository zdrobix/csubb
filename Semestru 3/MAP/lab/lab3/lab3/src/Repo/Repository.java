package repo;

import domain.Entity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {

    Optional<E> findOne(ID id) throws SQLException, IOException;

    Iterable<E> findAll() throws SQLException;

    Optional<E> save(E entity) throws SQLException;


    Optional<E> delete(ID id) throws SQLException;

    Optional<E> update(E entity) throws SQLException;

}


