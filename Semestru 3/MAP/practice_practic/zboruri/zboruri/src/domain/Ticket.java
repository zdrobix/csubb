package domain;

import java.time.LocalDateTime;

public class Ticket extends Entity<Long>{
    private String username;
    private Long flightId;
    private LocalDateTime purchaseTime;
    
    public Ticket (String username, Long flightId, LocalDateTime purchaseTime) {
        this.username = username;
        this.flightId = flightId;
        this.purchaseTime = purchaseTime;
    }
    
    public String getUsername() {
        return this.username;
    }

    public Long getFlightId() {
        return this.flightId;
    }

    public LocalDateTime getPurhaseTime() {
        return this.purchaseTime;
    }
}   
