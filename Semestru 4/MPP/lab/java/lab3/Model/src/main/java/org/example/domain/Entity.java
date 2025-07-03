package org.example.domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID Id;
    public Entity() {};

    public ID GetId() {
        return this.Id;
    }

    public Entity<ID> SetId(ID Id) {
        this.Id = Id;
        return this;
    }
}
