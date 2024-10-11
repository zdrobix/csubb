package Repo;

public class User extends Entity<Long>{
    private String nume;
    private String prenume;

    @Override
    public String toString() {
        return "Nume: " + this.nume + " Prenume: " + this.prenume;
    }

    public String getNume() {
        return this.nume;
    }

    public String getPrenume() {
        return this.prenume;
    }
}
