package domain;

import java.time.LocalDateTime;

public class Flight extends Entity<Long>{
    private String from;
    private String to;
    private LocalDateTime departure;
    private LocalDateTime arival;
    private int seats;

    public Flight (String from, String to, LocalDateTime departure, LocalDateTime arival, int seats) {
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arival = arival;
        this.seats = seats;
    }

    public String getFrom() {
        return this.from;
    }
    
    public String getTo() {
        return this.to;
    }

    public LocalDateTime getDeparture() {
        return this.departure;
    }

    public LocalDateTime getArival() {
        return this.arival;
    }

    public int getSeats() {
        return this.seats;
    }
}
