package domain;

public class Person extends Entity<Long> {
    private String username;
    private String name;

    public Person (String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }
}