package repo.db;

import domain.Utilizator;
import domain.validators.Validator;
import logs.Logger;
import repo.Repository;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class UserDatabaseRepository implements Repository<Long, Utilizator> {

    private final List<String> connectionListCredentials;
    private final Validator<Utilizator> validator;
    private final static Logger logger = new Logger();

    public UserDatabaseRepository(String url, String username, String password, Validator<Utilizator> validator_)  {
        this.connectionListCredentials = Arrays.asList(url, username, password);
        this.validator = validator_;
    }

    public static Connection connectToDb (List<String> connectionCredentials)  {
        boolean connected = false;
        Connection connection = null;
        SQLException exception = null;
        try {
            connection = DriverManager
                    .getConnection(
                            connectionCredentials.get(0),
                            connectionCredentials.get(1),
                            connectionCredentials.get(2)
                    );
            connected = true;
        } catch (SQLException e) {   exception = e;
        } finally {
            logger.LogConnection(connected);
            return connection;
        }
    }

    public Optional<Utilizator> findOne(Long id) throws SQLException {
        if (id == null)
            throw new IllegalArgumentException("id is null");
        var connection = connectToDb(this.connectionListCredentials);
        Utilizator user = null;
        if (connection == null)
            return Optional.empty();
        PreparedStatement statement = connection
                                    .prepareStatement("SELECT * FROM USERS WHERE ID = ?");

        statement.setLong(1, id);
        var result = statement.executeQuery();
        if (result.next()) {
            user = new Utilizator(
                    result.getString(2),
                    result.getString(3)
            );
            user.setId(
                    result.getLong(1));
        }
        result.close();
        connection.close();
        logger.LogModify("findOne", user.toString());
        return Optional.ofNullable(user);
    }

    public Iterable<Utilizator> findAll() throws SQLException {
        var connection = connectToDb(this.connectionListCredentials);
        if (connection == null)
            return null;

        var result = connection
                .prepareStatement("SELECT * FROM USERS")
                .executeQuery();

        Map<Long, Utilizator> users = new HashMap<>();
        while (result.next()) {
            users.putIfAbsent(
                    result.getLong(1),
                    new Utilizator(
                            result.getString(2),
                            result.getString(3)
                    )
            );
        }
        result.close();
        connection.close();
        logger.LogModify("findAll", "");
        return users.values();
    }

    public Optional<Utilizator> save(Utilizator user) throws SQLException {
        if (user == null)
            throw new IllegalArgumentException("user is null");
        validator.validate(user);
        var connection = connectToDb(this.connectionListCredentials);
        if (connection == null)
            return Optional.empty();

        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO USERS (id, first_name, last_name) VALUES (?, ?, ?)");
        statement.setLong(1, user.getId());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.executeUpdate();
        logger.LogModify("save", user.toString());
        return Optional.of(user);
    }


    public Optional<Utilizator> delete(Long id) throws SQLException {
        if (id == null)
            throw new IllegalArgumentException("id is null");
        var userToDelete = this.findOne(id).get();
        if (userToDelete == null)
            return Optional.empty();
        var connection = connectToDb(this.connectionListCredentials);
        if (connection == null)
            return Optional.empty();
        PreparedStatement statement = connection
                .prepareStatement("DELETE FROM USERS WHERE ID = ?");
        statement.setLong(1, id);
        statement.executeUpdate();
        logger.LogModify("delete", userToDelete.toString());
        return Optional.of(userToDelete);
    }

    public Optional<Utilizator> update(Utilizator user) {
        if (user == null)
            throw new IllegalArgumentException("user is null");
        validator.validate(user);
        return Optional.empty();
    }

    public int size() throws SQLException {
        int count = 0;
        for (var _ : this.findAll()) {
            count++;
        }
        return count;
    }
}


