package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
                        "jdbc:postgresql://localhost:5432/zboruri",
                        "postgres",
                        "password"
                    );
        } catch (SQLException e) {   
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public List<Client> getClients() throws SQLException{
        var connection = this.connectToDb();
        List<Client> clients = new ArrayList<>();
        var query = connection
            .prepareStatement("SELECT * FROM clients");
        var result = query.executeQuery();
        while (result.next()) {
            clients.add(
                (Client) new Client(result.getString(2), result.getString(3)).setId(result.getLong(1))
            );
        }
        result.close();
        connection.close();
        return clients;
    }

    public List<Flight> getFlights() throws SQLException {
        var connection = this.connectToDb();
        List<Flight> flights = new ArrayList<>();
        var query = connection
            .prepareStatement("SELECT * FROM clients");
        var result = query.executeQuery();
        while (result.next()) {
            flights.add(
                (Flight) new Flight(
                    result.getString(2), 
                    result.getString(3), 
                    result.getTimestamp(4).toLocalDateTime(), 
                    result.getTimestamp(5).toLocalDateTime(), 
                    result.getInt(6))
                    .setId(result.getLong(1))
            );
        }
        result.close();
        connection.close();
        return flights;
    }

    public List<Ticket> getTickets() throws SQLException {
        var connection = this.connectToDb();
        List<Ticket> tickets = new ArrayList<>();
        var query = connection
            .prepareStatement("SELECT * FROM clients");
        var result = query.executeQuery();
        while (result.next()) {
            tickets.add(
                (Ticket) new Ticket(
                    result.getString(2), 
                    result.getLong(3),
                    result.getTimestamp(4).toLocalDateTime()) 
                    .setId(result.getLong(1))
            );
        }
        result.close();
        connection.close();
        return tickets;
    }
}
