package domain;

import java.time.LocalDateTime;


public class Prietenie extends Entity<Long> {

    LocalDateTime date;
    Long idFriend1;
    Long idFriend2;

    public Prietenie() {
        this.date = LocalDateTime.now();
    }

    public Prietenie(Long idFriend1_, Long idFriend2_, LocalDateTime date_) {
        this.idFriend1 = idFriend1_;
        this.idFriend2 = idFriend2_;
        this.date = date_;
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
