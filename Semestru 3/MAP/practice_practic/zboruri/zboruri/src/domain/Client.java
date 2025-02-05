package domain;

public class Client extends Entity<Long> {
    private String username;
    private String name;

    public Client (String username, String name) {
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