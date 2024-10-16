package domain;

import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long>{
    private String firstName;
    private String lastName;
    private List<Utilizator> prieteni;

    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Utilizator> getFriends() {
        return this.prieteni;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", friends=" + this.prieteni +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }
}