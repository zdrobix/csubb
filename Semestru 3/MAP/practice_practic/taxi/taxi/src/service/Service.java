package service;

import utils.Observable;
import utils.Observer;
import utils.EventChange;
import repo.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.Chauffer;
import domain.Order;
import domain.Person;


public class Service implements Observable<EventChange>{
    private Repository Repo;
    private List<Observer<EventChange>> observers = new ArrayList<>();
    
    public Service(Repository repo) {
        Repo = repo;
    }

    public List<Person> getPersons() {
        try {
            return Repo.getAllPersons();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Chauffer> getChauffers() {
        try {
            return Repo.getAllChauffers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Order> getOrders() {
        try {
            return Repo.getAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addOrder(Person person, Chauffer chauffer ) {
        try {
            var order = new Order(person, chauffer, LocalDateTime.now());
            Repo.addOrder(order);
            notifyObservers(new EventChange("add", order, null));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRequest (Person person, Chauffer chauffer) {
        notifyObservers(new EventChange("order", new Order(person, chauffer, null), null));
    }

    @Override
    public void addObserver(Observer<EventChange> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EventChange> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EventChange t) {
        for (Observer<EventChange> observer : observers) {
            observer.update(t);
        }
    }
}
