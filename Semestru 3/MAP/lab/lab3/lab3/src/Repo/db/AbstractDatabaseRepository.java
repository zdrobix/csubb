package repo.db;

import domain.Entity;
import domain.validators.Validator;
import repo.memory.InMemoryRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractDatabaseRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private Connection connection;
    private static String logFilename = "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\logs\\logs.txt";

    public AbstractDatabaseRepository(String url, String username, String password, Validator<E> validator) throws SQLException, IOException {
        super(validator);
        this.establishConnection(url, username, password);
    }

    private void establishConnection(String url, String username, String password) throws SQLException, IOException {
        var fileWriter = new FileWriter(logFilename, true);
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            if (this.connection != null) {
                fileWriter.append("Successfully connected to the database. " + LocalDateTime.now());
            }
            this.loadDatabase();
        } catch (SQLException e) {
            fileWriter.append("Failed to connect to the database. " + LocalDateTime.now());
        } finally {
            fileWriter.close();
        }
    }

    private void loadDatabase() throws SQLException {
        var result = this.connection
                        .prepareStatement(
                                "SELECT * FROM ")
                        .executeQuery();
        while (result.next()) {
            super.save(
                    this.extractEntity(
                            Arrays.asList(
                                    result.getString(0),
                                    result.getString(1),
                                    result.getString(2)
                            )
                    )
            );
        }
        this.connection.close();
    }



    public abstract E extractEntity(List<String> attributes);
}


