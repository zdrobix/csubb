package domain;

import java.time.LocalDateTime;


public class Prietenie extends Entity<Long> {

    LocalDateTime date;
    Long idFriend1;
    Long idFriend2;

    public Prietenie() {
        this.date = LocalDateTime.now();
    }

    public Prietenie(Long idFriend1, Long idFriend2) {
        this.idFriend1 = idFriend1;
        this.idFriend2 = idFriend2;
    }

    public Long getIdFriend1() {
        return idFriend1;
    }

    public Long getIdFriend2() {
        return idFriend2;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
