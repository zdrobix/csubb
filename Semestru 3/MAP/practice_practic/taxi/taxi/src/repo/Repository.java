package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.*;

public class Repository {
    public Repository() {
    }

    public Connection connectToDb ()  {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "password"
                    );
        } catch (SQLException e) {   
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        var connection = connectToDb();
        var query = connection
            .prepareStatement("SELECT * FROM persons WHERE car IS NULL");
        var result = query.executeQuery();
        while(result.next())
        {
            persons.add(
                (Person) new Person(result.getString(2), result.getString(3)).setId(result.getLong(1))
            );
        }
        result.close();
        connection.close();
        return persons;
    }

    public List<Chauffer> getAllChauffers() throws SQLException {
        List<Chauffer> chauffers = new ArrayList<>();
        var connection = connectToDb();
        var query = connection
            .prepareStatement("SELECT * FROM persons WHERE car IS NOT NULL");
        var result = query.executeQuery();
        while(result.next())
        {
            chauffers.add(
                (Chauffer) new Chauffer(result.getString(2), result.getString(3), result.getString(4)).setId(result.getLong(1))
            );
        }
        result.close();
        connection.close();
        return chauffers;
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        var connection = connectToDb();
        var query = connection
            .prepareStatement("SELECT * FROM orders");
        var result = query.executeQuery();
        while(result.next())
        {
            orders.add(
                (Order) new Order(
                    this.getAllPersons().stream().filter(person -> {
                        try {
                            return person.getId() == result.getLong(2);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }).findFirst().get(),
                    this.getAllChauffers().stream().filter(chauffer -> {
                        try {
                            return chauffer.getId() == result.getLong(3);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }).findFirst().get(),
                    result.getTimestamp(4).toLocalDateTime()
                ).setId(result.getLong(1))

            );
        }
        result.close();
        connection.close();
        return orders;
    }

    public void addOrder (Order order) throws SQLException {
        var connection = connectToDb();
        var query = connection
            .prepareStatement("INSERT INTO orders (id_person, id_chauffer, date) VALUES (?, ?, ?)");
        query.setLong(1, order.getPerson().getId());
        query.setLong(2, order.getChauffer().getId());
        query.setTimestamp(3, java.sql.Timestamp.valueOf(order.getDate()));
        query.executeUpdate();
        connection.close();
    }

}
