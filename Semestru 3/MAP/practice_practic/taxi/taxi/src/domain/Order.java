package domain;

import java.time.LocalDateTime;

public class Order extends Entity<Long> {
    private Person person;
    private Chauffer chauffer;
    private LocalDateTime date;

    public Order(Person person, Chauffer chauffer, LocalDateTime date) {
        this.person = person;
        this.chauffer = chauffer;
        this.date = date;
    }

    public Person getPerson() {
        return this.person;
    }   

    public Chauffer getChauffer() {
        return this.chauffer;
    }

    public LocalDateTime getDate() {
        return this.date;
    }
}
