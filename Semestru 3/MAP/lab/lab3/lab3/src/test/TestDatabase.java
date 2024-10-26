package test;

import domain.validators.PrietenieValidator;
import domain.validators.UtilizatorValidator;
import repo.db.UserDatabaseRepository;
import repo.db.FriendshipDatabaseRepository;


import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {
    @Test
    public void test_All() {

    }

    @Test
    public void test_create() throws SQLException, IOException {
        UserDatabaseRepository repo = new UserDatabaseRepository(
                "jdbc:postgresql://localhost:5432/socialnetwork",
                "postgres",
                "parola",
                new UtilizatorValidator()
        );

        FriendshipDatabaseRepository repoPrieteni = new FriendshipDatabaseRepository(
                "jdbc:postgresql://localhost:5432/socialnetwork",
                "postgres",
                "parola",
                new PrietenieValidator()
        );

    }
}
