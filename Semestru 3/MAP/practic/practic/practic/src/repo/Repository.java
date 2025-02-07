package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.SeatReservation;
import domain.Show;
import domain.User;

public class Repository {
    public Repository() {};

    public Connection connectToDb ()  {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(
                        "jdbc:postgresql://localhost:5432/spectacole",
                        "postgres",
                        "password"
                    );
        } catch (SQLException e) {   System.out.println(e.getMessage());
        }
        return connection;
    }

    public List<User> getUsers() throws SQLException{
        var connection = connectToDb();
        List<User> users = new ArrayList<>();
        var query = connection
            .prepareStatement("SELECT * FROM users");
        var result = query.executeQuery();
        while(result.next())
        {
            users.add(
                new User(result.getInt(1), result.getString(2), result.getString(3))
            );
        }
        result.close();
        connection.close();
        return users;
    }

    public List<Show> getShows(int pageNumber, boolean getAll) throws SQLException {
        var connection = connectToDb();
        List<Show> shows = new ArrayList<>();
        var query = connection
            .prepareStatement("SELECT * FROM shows ORDER BY creationdate");
        var result = query.executeQuery();
        int i = 0;
        while (result.next()) {
            if ((i >= pageNumber * 3 && i < pageNumber * 3 + 3) || getAll)
                shows.add(
                    new Show(
                        result.getInt(1),
                        result.getString(2),
                        result.getInt(3),
                        result.getTimestamp(4).toLocalDateTime(),
                        result.getInt(5),
                        result.getTimestamp(6).toLocalDateTime()
                    )
                );
            i ++;
        }
        result.close();
        connection.close();
        return shows;
    }

    public List<SeatReservation> getSeatsReservations() throws SQLException{
        var connection = connectToDb();
        List<SeatReservation> seats = new ArrayList<>();
        var query = connection
            .prepareStatement("SELECT * FROM seats");
        var result = query.executeQuery();
        while (result.next()) {
            seats.add(
                new SeatReservation(result.getInt(1), result.getString(2))
            );
        }
        result.close();
        connection.close();
        return seats;
    }

    public void addShow (Show show) throws SQLException {
        var connection = connectToDb();
        var query = connection.prepareStatement(
            "INSERT INTO shows (name, durationMinutes, startDate, numberOfSeats, creationDate) VALUES (?, ?, ?, ?, ?)"
        );
        query.setString(1, show.getName());
        query.setInt(2, show.getDurationMinutes());
        query.setTimestamp(3, java.sql.Timestamp.valueOf(show.getStartDate()));
        query.setInt(4, show.getNumberSeats());
        query.setTimestamp(5, java.sql.Timestamp.valueOf(show.getCreationDate()));
        query.executeUpdate();
        connection.close();
    }

    public void addReservation(int showId, User user) throws SQLException {
        var connection = connectToDb();
        var query = connection.prepareStatement(
            "INSERT INTO seats (showId, email) VALUES (?, ?)"
        );
        query.setInt(1, showId);
        query.setString(2, user.getEmail());
        query.executeUpdate();
        connection.close();
    }
}
