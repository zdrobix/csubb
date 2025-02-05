package domain;

public class Entity<ID> {
    private ID Id;
    public Entity(){};
    public ID getId() {
        return this.Id;
    }   
    public Entity<ID> setId (ID Id) {
        this.Id = Id;
        return this;
    }
}