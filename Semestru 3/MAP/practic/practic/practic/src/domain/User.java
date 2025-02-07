package domain;

public class User {
    private int Id;
    private String email;
    private String name;

    public User (int Id, String email, String name) {
        this.Id = Id;
        this.email = email;
        this.name = name;
    }

    public int getId() {
        return this.Id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }
}
