package template.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    public Repository() {
    }

    public Connection connectToDb ()  {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(
                        "jdbc:postgresql://localhost:5432/NAME_OF_DATABASE",
                        "postgres",
                        "password"
                    );
        } catch (SQLException e) {   
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public List<Object> getAllObjects() throws SQLException {
        List<Object> objects = new ArrayList<>();
        var connection = connectToDb();
        var query = connection
            .prepareStatement("SELECT * FROM your_table");
        var result = query.executeQuery();
        while(result.next())
        {
            objects.add(
                new Object()
            );
        }
        result.close();
        connection.close();
        return objects;
    }

    public void addObject(Object object) throws SQLException {
        var connection = connectToDb();
        var query = connection
            .prepareStatement("INSERT INTO your_table VALUES (?,?,?)");
        query.setInt(1, 1);
        query.setInt(2, 1);
        query.setInt(3, 1);
        query.executeUpdate();
        connection.close();
    }

    public void updateObject(Object object) throws SQLException {
        var connection = connectToDb();
        var query = connection
            .prepareStatement("UPDATE your_table SET column2 = ?, column3 = ? WHERE id = ?");
        query.setInt(1, 1);
        query.setInt(2, 1);
        query.setInt(3, 1);     //objct.getId()
        query.executeUpdate();
        connection.close();
    }

    public void deleteObject(Object object) throws SQLException {
        var connection = connectToDb();
        var query = connection
            .prepareStatement("DELETE FROM your_table WHERE id = ?");
        query.setInt(1, 1);     //object.getId()
        query.executeUpdate();
        connection.close();
    }
}
