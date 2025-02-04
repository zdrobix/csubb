package domain;

public class Entity<ID> {
    private ID id;

    public Entity() {
    }

    public ID getId() {
        return id;
    }

    public Entity<ID> setId(ID id) {
        this.id = id;
        return this;
    }
}
