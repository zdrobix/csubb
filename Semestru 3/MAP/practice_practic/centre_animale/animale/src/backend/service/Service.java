package backend.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import backend.domain.AdoptionCenter;
import backend.domain.Animal;
import backend.domain.AnimalType;
import backend.repo.RepoCenterAnimal;
import backend.utils.EventChange;
import backend.utils.Observable;
import backend.utils.Observer;

public class Service implements Observable<EventChange>{
    private RepoCenterAnimal Repo;
    private List<Observer<EventChange>> observers=new ArrayList<>();

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

    public List<Animal> getAnimalsOfTypeFromCenter(AnimalType type, int centerID) {
        try {
            return Repo.getAnimalsOfTypeFromCenter(type, centerID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void TransferAnimal(Animal animal, AdoptionCenter center) {
        try {
            Repo.TransferAnimal(animal, center);
            notifyObservers(new EventChange(new Animal(animal.getID(), animal.getName(), center.getID(), animal.getType()), animal, "transfer"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void InitializeTransfer(Animal animal) {
        notifyObservers(new EventChange(animal, null, "request"));
    }

    @Override
    public void addObserver(Observer<EventChange> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EventChange> e) {
    }

    @Override
    public void notifyObservers(EventChange t) {
        observers.stream().forEach(x->x.update(t));
    }
}
