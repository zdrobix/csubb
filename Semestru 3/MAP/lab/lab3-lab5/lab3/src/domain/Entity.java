package domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {

    private ID id;
    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }
}