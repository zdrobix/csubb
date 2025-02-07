package domain;

import java.time.LocalDateTime;

public class Show {
    private int Id;
    private String name;
    private int durationMinutes;
    private LocalDateTime startDate;
    private int numberOfSeats;
    private LocalDateTime creationDate;

    public Show (int id, String name, int duration, LocalDateTime start, int number, LocalDateTime creation) {
        this.Id = id;
        this.name = name;
        this.durationMinutes = duration;
        this.startDate = start;
        this.numberOfSeats = number;   
        this.creationDate = creation;
    }

    public int getId() {
        return this.Id;
    }

    public String getName() {
        return this.name;
    }

    public int getDurationMinutes() {
        return this.durationMinutes;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public int getNumberSeats() {
        return this.numberOfSeats;
    }
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }
}
