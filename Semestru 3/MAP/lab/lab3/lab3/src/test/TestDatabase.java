package test;

import domain.validators.UtilizatorValidator;
import repo.db.UserDatabaseRepository;

import repo.file.UtilizatorFile;

import java.sql.SQLException;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {
    @Test
    public void test_All() {

    }

    @Test
    public void test_create() throws SQLException {
        UserDatabaseRepository repo = new UserDatabaseRepository(
                "jdbc:sqlserver://DESKTOP-9PS0RE2:1433;databaseName=socialnetwork;",
                "socialnetworkUser",
                "user10",
                new UtilizatorValidator()
        );
        UtilizatorFile repo2 = new UtilizatorFile(
                "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\users.txt",
                new UtilizatorValidator()
        );

        var allUsers = repo2.findAll();
        for (var user : allUsers) {
            repo.save(user);
        }
    }
}
