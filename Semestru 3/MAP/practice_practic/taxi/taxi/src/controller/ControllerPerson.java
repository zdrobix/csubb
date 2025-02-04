package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import service.Service;
import domain.Person;

public class ControllerPerson {
    private Service Service;
    private Person Person;

    @FXML
    private VBox mainVBox;

    public void setService(Service service, Person person) {
        this.Service = service;
        this.Person = person;
        this.initElements();
    }

    public void initElements() {
        this.mainVBox.getChildren().add(new Label("Client " + this.Person.getName()));
    }
}
