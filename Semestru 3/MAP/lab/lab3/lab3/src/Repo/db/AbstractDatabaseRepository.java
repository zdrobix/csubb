package repo.db;

import domain.Entity;
import domain.validators.Validator;
import repo.Repository;
import repo.memory.InMemoryRepository;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDatabaseRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    private List<String> connectionCredentials;
    private static String logFilename = "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\logs\\logs.txt";

    public AbstractDatabaseRepository(String url, String username, String password, Validator<E> validator)  {
        this.connectionCredentials = Arrays.asList(url, username, password);
    }

    public Connection connectToDb () throws SQLException, IOException{
        boolean connected = false;
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(
                            this.connectionCredentials.get(0),
                            this.connectionCredentials.get(1),
                            this.connectionCredentials.get(2)
                    );
            connected = true;
        } catch (SQLException e) {   connected = false;
        } finally {
            try {
                FileWriter fw = new FileWriter(
                        logFilename,
                        true);
                if (connected)
                    fw.append("Connection established. " + LocalDateTime.now());
                else
                    fw.append("Connection failed. " + LocalDateTime.now());
                fw.close();
            } catch (IOException ie) { throw new FileNotFoundException(logFilename);
            } finally { return connection; }
        }
    }

    public Optional<E> findOne(ID id) throws SQLException, IOException {
        var connection = this.connectToDb();
        var resultReturn = Optional.empty();
        if (connection == null)
            return Optional.empty();
        var result = connection
                            .prepareStatement("SELECT * FROM USERS WHERE ID = ?")
                            .executeQuery();
        if (result.next()) {
            resultReturn = Optional.of(
                                    this.extractEntity(
                                            Arrays.asList(
                                                    result.getString(1),
                                                    result.getString(2),
                                                    result.getString(3)
                                            )
                                    )
            );
        }
        result.close();connection.close();
        return resultReturn;
    }

    public Iterable<E> findAll() {
        return null;
    }

    public Optional<E> save(E entity) {
        return null;
    }


    public Optional<E> delete(ID id) {
        return null;
    }

    public Optional<E> update(E entity) {
        return null;
    }

    public abstract E extractEntity(List<String> attributes);
}


