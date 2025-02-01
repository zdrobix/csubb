package backend.controller;


package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

import backend.domain.*;
import backend.service.Service;
import backend.App;

public class Controller1  { 
    private Service ServiceA;

    public void setService(Service service) {
        this.ServiceA = service;
    }

    public void initialize2() {
        for (var center : ServiceA.getCenters()) {
            var stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/view-center.fxml"));
            
        }
    }

}
