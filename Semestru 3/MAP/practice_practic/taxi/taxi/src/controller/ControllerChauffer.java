package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import service.Service;
import domain.Chauffer;

public class ControllerChauffer {
    private Service Service;
    private Chauffer Chauffer;

    @FXML
    private VBox mainVBox;


    public void setService(Service service, Chauffer chauffer) {
        this.Service = service;
        this.Chauffer = chauffer;
        this.initElements();
    }

    public void initElements() {
        this.mainVBox.getChildren().add(new Label ("Chauffer " + this.Chauffer.getName()));
    }
}
