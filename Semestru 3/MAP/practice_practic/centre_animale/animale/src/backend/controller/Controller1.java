package backend.controller;


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
    private AdoptionCenter Center;

    ObservableList<Animal> model = FXCollections.observableArrayList();

    private Animal SelectedAnimal;

    @FXML
    private Label centerLabel,addressLabel,occupancyLabel;

    @FXML 
    private TableView<Animal> animalTable;
    @FXML
    private TableColumn<Animal, String> animalName;
    @FXML
    private TableColumn<Animal, String> animalType;

    public void setService(Service service, AdoptionCenter center) {
        this.ServiceA = service;
        this.Center = center;
        this.model.setAll(this.ServiceA.getAnimalsFromCenter(this.Center.getID()));
        this.initialize2();
    }

    public void initialize2() {
        this.centerLabel.setText("Center: " + Center.getName());
        this.addressLabel.setText("Address: " + Center.getAddress());
        this.occupancyLabel.setText("Occupancy: " + this.ServiceA.getAnimalsFromCenter(this.Center.getID()).size() * 1.0 /this.Center.getCapacity() * 100 + "% full");
        this.animalName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.animalType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        this.animalTable.setItems(model);
        this.animalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.SelectedAnimal = newSelection;
                System.out.println(this.SelectedAnimal.getName());
            }
        });
        this.animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        System.out.println(this.centerLabel.getText());
    }
}
