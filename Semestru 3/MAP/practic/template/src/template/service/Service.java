package template.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import template.utils.EventChange;
import template.utils.Observable;
import template.utils.Observer;
import template.repo.Repository;

public class Service implements Observable<EventChange> {
    private Repository Repo;
    private List<Observer<EventChange>> observers = new ArrayList<>();
    
    public Service(Repository repo) {
        this.Repo = repo;
    }

    public void add(Object obj) {
        try {
            this.Repo.addObject(obj);
            notifyObservers(new EventChange("add", obj, null));
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(Object obj) {
        try {
            var oldObject = this.Repo.getAllObjects().stream().filter(x -> x.equals(obj)).findFirst().get();  //boxing
            this.Repo.updateObject(obj);
            notifyObservers(new EventChange("update", obj, oldObject));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Object obj) {
        try {
            this.Repo.deleteObject(obj);
            notifyObservers(new EventChange("delete", obj, null));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object> getAll() {
        try {
            return this.Repo.getAllObjects();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
