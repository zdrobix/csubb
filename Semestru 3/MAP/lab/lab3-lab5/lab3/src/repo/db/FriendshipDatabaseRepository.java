package repo.db;

import domain.Utilizator;
import domain.validators.Validator;
import logs.Logger;
import repo.Repository;
import domain.Tuple;
import domain.Prietenie;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FriendshipDatabaseRepository implements Repository<Tuple<Long, Long>, Prietenie> {

    private List<String> connectionListCredentials;
    private Validator<Prietenie> validator;

    public FriendshipDatabaseRepository(String url, String username, String password, Validator<Prietenie> validator_)  {
        this.connectionListCredentials = Arrays.asList(url, username, password);
        this.validator = validator_;
    }

    @Override
    public Optional<Prietenie> findOne(Tuple<Long, Long> ID) throws SQLException, IOException {
        if (ID == null)
            throw new IllegalArgumentException("ID cannot be null");
        var connection = UserDatabaseRepository
                .connectToDb(this.connectionListCredentials);
        if (connection == null)
            return Optional.empty();

        PreparedStatement statement = connection
                .prepareStatement("SELECT * FROM FRIENDS WHERE id_friend1 = ? AND id_friend2 = ?");
        statement.setLong(1, max(ID.getLeft(),ID.getRight()));
        statement.setLong(2, min(ID.getLeft(),ID.getRight()));
        ResultSet result = statement.executeQuery();

        Prietenie prietenie = null;
        if (result.next()) {
            prietenie = new Prietenie(
                    max(result.getLong(1),
                            result.getLong(2)),
                    min(result.getLong(1),
                            result.getLong(2)),
                    result.getDate(3).toLocalDate()
            );
        }
        result.close();
        connection.close();
        return Optional.of(prietenie);
    }

    @Override
    public Iterable<Prietenie> findAll() throws SQLException, IOException {
        var connection = UserDatabaseRepository
                .connectToDb(this.connectionListCredentials);
        if (connection == null)
            return null;
        Map<Tuple<Long, Long>, Prietenie> friends = new HashMap<>();

        var result = connection
                .prepareStatement("SELECT * FROM FRIENDS")
                .executeQuery();

        while (result.next()) {
            var id1 = max(result.getLong(1),
                    result.getLong(2));
            var id2 = min(result.getLong(1),
                    result.getLong(2));
            friends.putIfAbsent(
                    new Tuple(
                            id1,
                            id2
                    ),
                    new Prietenie(
                            id1,
                            id2,
                            result.getDate(3).toLocalDate()
                    )
            );
        }
        result.close();
        connection.close();
        return friends.values();
    }

    @Override
    public Optional<Prietenie> save(Prietenie prietenie) throws SQLException, IOException {
        if (prietenie == null)
            throw new IllegalArgumentException("Friendship cannot be null");
        var connection = UserDatabaseRepository
                .connectToDb(this.connectionListCredentials);
        if (connection == null)
            return Optional.empty();

        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO FRIENDS (id_friend1, id_friend2, date) VALUES (?, ?, ?)");

        statement.setLong(1,
                max(prietenie.getIdFriend1(),
                        prietenie.getIdFriend2()));
        statement.setLong(2,
                min(prietenie.getIdFriend1(),
                        prietenie.getIdFriend2()));
        statement.setDate(3, Date.valueOf(prietenie.getDate()));
        statement.executeUpdate();
        return Optional.of(prietenie);
    }

    @Override
    public Optional<Prietenie> delete(Tuple<Long, Long> ID) throws SQLException, IOException {
        if (ID == null)
            throw new IllegalArgumentException("ID cannot be null");
        var connection = UserDatabaseRepository
                .connectToDb(this.connectionListCredentials);
        if (connection == null)
            return Optional.empty();
        Prietenie prietenieToDelete = this.findOne(ID).get();
        if (prietenieToDelete == null)
            return Optional.empty();

        PreparedStatement statement = connection
                .prepareStatement("DELETE FROM FRIENDS WHERE id_friend1 = ? AND id_friend2 = ?;");
        statement.setLong(1,
                max(ID.getLeft(),
                        ID.getRight()));
        statement.setLong(2,
                min(ID.getLeft(),
                        ID.getRight()));
        statement.executeUpdate();

        return Optional.of(prietenieToDelete);
    }

    @Override
    public Optional<Prietenie> update(Prietenie prietenie) throws SQLException {
        if (prietenie == null)
            throw new IllegalArgumentException("user is null");
        validator.validate(prietenie);
        return Optional.empty();
    }

    public int size() throws SQLException, IOException {
        AtomicInteger count = new AtomicInteger();
        this.findAll().forEach(
                _ -> count.getAndIncrement()
        );
        return count.get();
    }
}
