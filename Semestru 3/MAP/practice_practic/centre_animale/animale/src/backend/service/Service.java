package backend.service;

import java.sql.SQLException;
import java.util.List;

import javax.print.DocFlavor.READER;

import backend.domain.AdoptionCenter;
import backend.domain.Animal;
import backend.repo.RepoCenterAnimal;

public class Service {
    private RepoCenterAnimal Repo;

    public Service (RepoCenterAnimal repo) {
        this.Repo = repo;
    }

    public List<Animal> getAnimalsFromCenter(int centerID) {
        try {
            return Repo.getAnimalsFromCenter(centerID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<AdoptionCenter> getCenters() {
        try {
            return Repo.getCenters();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    
}
