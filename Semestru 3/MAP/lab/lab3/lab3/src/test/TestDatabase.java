package test;

import domain.Utilizator;
import domain.validators.UtilizatorValidator;
import repo.db.UserDatabaseRepository;

import repo.file.UtilizatorFile;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {
    @Test
    public void test_All() {

    }

    @Test
    public void test_create() throws SQLException {
        UserDatabaseRepository repo = new UserDatabaseRepository(
                "jdbc:postgresql://localhost:5432/socialnetwork",
                "postgres",
                "parola",
                new UtilizatorValidator()
        );


    }
}
