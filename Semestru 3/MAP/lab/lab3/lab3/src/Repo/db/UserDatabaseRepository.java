package repo.db;

import domain.Utilizator;
import domain.validators.Validator;
import repo.Repository;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class UserDatabaseRepository implements Repository<Long, Utilizator> {

    private List<String> connectionCredentials;
    private Validator<Utilizator> validator;
    private static String logFilename = "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\logs\\logs.txt";

    public UserDatabaseRepository(String url, String username, String password, Validator<Utilizator> validator_)  {
        this.connectionCredentials = Arrays.asList(url, username, password);
        this.validator = validator_;
    }

    public Connection connectToDb () {
        boolean connected = false;
        Connection connection = null;
        SQLException exception = null;
        try {
            connection = DriverManager
                    .getConnection(
                            this.connectionCredentials.get(0),
                            this.connectionCredentials.get(1),
                            this.connectionCredentials.get(2)
                    );
            connected = true;
        } catch (SQLException e) {   connected = false; exception = e;
        } finally {
            try {
                FileWriter fw = new FileWriter(
                        logFilename,
                        true);
                if (connected)
                    fw.append("Connection established. " + LocalDateTime.now() + '\n');
                else {
                    fw.append("Connection failed. " + LocalDateTime.now() + '\n');
                    fw.append(exception.getSQLState() + '\n');
                    fw.append(exception.getMessage() + '\n');
                    exception.printStackTrace();
                }
                fw.close();
            } catch (IOException ie) { throw new FileNotFoundException(logFilename);
            } finally { return connection; }
        }
    }

    public Optional<Utilizator> findOne(Long id) throws SQLException {
        if (id == null)
            throw new IllegalArgumentException("id is null");
        var connection = this.connectToDb();
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
        return Optional.ofNullable(user);
    }

    public Iterable<Utilizator> findAll() throws SQLException {
        var connection = this.connectToDb();
        Map<Long, Utilizator> users = new HashMap<>();
        if (connection == null)
            return null;
        var result = connection
                .prepareStatement("SELECT * FROM USERS")
                .executeQuery();

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
        return users.values();
    }

    public Optional<Utilizator> save(Utilizator user) throws SQLException {
        if (user == null)
            throw new IllegalArgumentException("user is null");
        validator.validate(user);
        var connection = this.connectToDb();
        if (connection == null)
            return Optional.empty();
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO USERS (id, first_name, last_name) VALUES (?, ?, ?)");
        statement.setLong(1, user.getId());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.executeUpdate();
        return Optional.of(user);
    }


    public Optional<Utilizator> delete(Long id) throws SQLException {
        if (id == null)
            throw new IllegalArgumentException("id is null");
        var userToDelete = this.findOne(id);
        if (userToDelete.isEmpty())
            return Optional.empty();
        var connection = this.connectToDb();
        if (connection == null)
            return Optional.empty();
        PreparedStatement statement = connection
                .prepareStatement("DELETE FROM USERS WHERE ID = ?");
        statement.setLong(1, id);
        statement.executeUpdate();
        return Optional.of(userToDelete.get());
    }

    public Optional<Utilizator> update(Utilizator user) {
        if (user == null)
            throw new IllegalArgumentException("user is null");
        validator.validate(user);
        return Optional.empty();
    }

}


