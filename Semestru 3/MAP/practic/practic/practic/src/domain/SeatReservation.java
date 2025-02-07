package domain;

public class SeatReservation {
    private int showId;
    private String email;

    public SeatReservation (int showId, String email) {
        this.showId =showId;
        this.email =email;
    }

    public String getEmail() {
        return this.email;
    }

    public int getShowId() {
        return this.showId;
    }
}

