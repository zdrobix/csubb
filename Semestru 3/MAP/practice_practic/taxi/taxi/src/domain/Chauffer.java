package domain;

public class Chauffer extends Person {
    private String car;

    public Chauffer(String username, String name, String car) {
        super(username, name);
        this.car = car;
    }

    public String getCar() {
        return this.car;
    }
}
