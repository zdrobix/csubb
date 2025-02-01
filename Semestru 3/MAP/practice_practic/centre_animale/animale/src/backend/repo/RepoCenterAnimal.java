package backend.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.domain.AdoptionCenter;
import backend.domain.Animal;
import backend.domain.AnimalType;

public class RepoCenterAnimal {
    private List<String> connectionCredentials = new ArrayList<>();

    public RepoCenterAnimal() {
        this.connectionCredentials = List.of(
                "jdbc:postgresql://localhost:5432/socialnetwork",
                "postgres",
                "password"
        );
    }

    public Connection connectToDb ()  {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(
                        connectionCredentials.get(0),
                        connectionCredentials.get(1),
                        connectionCredentials.get(2)
                    );
        } catch (SQLException e) {   System.out.println(e.getMessage());
        }
        return connection;
    }

    public List<Animal> getAnimalsFromCenter(int centerID) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        var connection = connectToDb();
        var query = connection
            .prepareStatement("SELECT * FROM animals " +
                "WHERE center_id = ?");
        query.setInt(1, centerID);
        var result = query.executeQuery();
        while(result.next())
        {
            animals.add(
                new Animal(
                    result.getInt(1),
                    result.getString(2),
                    result.getInt(3),
                    AnimalType.valueOf(result.getString(4))
                    )
            );
        }
        result.close();
        connection.close();
        return animals;
    }

    public List<AdoptionCenter> getCenters() throws SQLException {
        List<AdoptionCenter> centers = new ArrayList<>();
        var connection = connectToDb();
        var query = connection
            .prepareStatement("SELECT * FROM AdoptionCenters");
        var result = query.executeQuery();
        while(result.next())
        {
            centers.add(
                new AdoptionCenter(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4)
                    )
            );
        }
        result.close();
        connection.close();
        return centers;
    }

}
