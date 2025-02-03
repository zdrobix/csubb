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

    public RepoCenterAnimal() {
    }

    public Connection connectToDb ()  {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(
                        "jdbc:postgresql://localhost:5432/animals",
                        "postgres",
                        "password"
                    );
        } catch (SQLException e) {   System.out.println(e.getMessage());
        }
        return connection;
    }

    public List<Animal> getAnimalsFromCenter(int centerID) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        var connection = connectToDb();
        var query = connection
            .prepareStatement("SELECT * FROM animalsplaced " +
                "WHERE centerid = ?");
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
            .prepareStatement("SELECT * FROM adoptioncenters");
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

    public List<Animal> getAnimalsOfTypeFromCenter(AnimalType type, int centerID) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        var connection = connectToDb();
        var query = connection
            .prepareStatement("SELECT * FROM animalsplaced " +
                "WHERE centerid = ? AND type = ?");
        query.setInt(1, centerID);
        query.setString(2, type.toString());
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

    public void TransferAnimal(Animal animal, AdoptionCenter center) throws SQLException{
        var connection = connectToDb();
        var query = connection
            .prepareStatement("UPDATE animalsplaced " +
                "SET centerid = ? " +
                "WHERE id = ?");
            query.setInt(1, center.getID());
            query.setInt(2, animal.getID());
        query.executeUpdate();
        connection.close();    
    }
}
