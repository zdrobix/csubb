import controller.Ui;

import domain.validators.PrietenieValidator;
import domain.validators.UtilizatorValidator;

import repo.db.UserDatabaseRepository;
import repo.db.FriendshipDatabaseRepository;

import service.Service;

public class Main {
    public static void main(String[] args)  {
        new Ui(
                new Service(
                        new UserDatabaseRepository(
                                "jdbc:postgresql://localhost:5432/socialnetwork",
                                "postgres",
                                "parola",
                                new UtilizatorValidator()
                        ),
                        new FriendshipDatabaseRepository(
                                "jdbc:postgresql://localhost:5432/socialnetwork",
                                "postgres",
                                "parola",
                                new PrietenieValidator()
                        )
                )).Run();
    }
}

