package template.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import template.service.Service;
import template.utils.EventChange;
import template.utils.Observer;

public class Controller implements Observer<EventChange> {
    private Service ServiceA;
    ObservableList<Object> model = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.ServiceA = service;
        this.ServiceA.addObserver(this);
        //this.model.setAll(this.ServiceA.getAll());
    }

    

    @Override
    public void update (EventChange e) {
        switch (e.getType()) {
            case "add":
                model.add(e.getData());
                break;
            case "update":
                model.remove(e.getOldData());
                model.add(e.getData());
                break;
            case "delete":
                model.remove(e.getData());
                break;
        }
    }
}