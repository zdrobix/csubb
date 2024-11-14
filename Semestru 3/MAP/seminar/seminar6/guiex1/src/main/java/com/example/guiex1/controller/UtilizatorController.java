package main.java.com.example.guiex1.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.example.guiex1.HelloApplication;
import main.java.com.example.guiex1.domain.Utilizator;
import main.java.com.example.guiex1.domain.ValidationException;
import main.java.com.example.guiex1.services.UtilizatorService;
import main.java.com.example.guiex1.utils.events.ChangeEventType;
import main.java.com.example.guiex1.utils.events.UtilizatorEntityChangeEvent;
import main.java.com.example.guiex1.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorController implements Observer<UtilizatorEntityChangeEvent> {
    UtilizatorService service;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField idTextField;

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,String> tableColumnFirstName;
    @FXML
    TableColumn<Utilizator,String> tableColumnLastName;


    public void setUtilizatorService(UtilizatorService service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableView.setItems(model);

        // Add a listener to populate text fields when a row is selected
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateTextFields(newValue);
            }
        });
    }

    private void populateTextFields(Utilizator utilizator) {
        firstNameTextField.setText(utilizator.getFirstName());
        lastNameTextField.setText(utilizator.getLastName());
    }

    private void initModel() {
        Iterable<Utilizator> messages = service.getAll();
        List<Utilizator> users = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);
    }

    @Override
    public void update(UtilizatorEntityChangeEvent utilizatorEntityChangeEvent) {
        switch (utilizatorEntityChangeEvent.getType()) {
            case ADD:
                this.model.add(
                        utilizatorEntityChangeEvent.getData()
                );
                break;
            case UPDATE:
                this.model.set(
                        this.tableView.getSelectionModel().getSelectedIndex(),
                        utilizatorEntityChangeEvent.getData()
                );
                break;
            case DELETE:
                this.model.remove(utilizatorEntityChangeEvent.getData());
                break;
        }
    }

    public void handleDeleteUtilizator(ActionEvent actionEvent) {
        Utilizator user=(Utilizator) tableView.getSelectionModel().getSelectedItem();
        if (user!=null) {
            Utilizator deleted= service.deleteUtilizator(user.getId());
        }
    }

    public void handleUpdateUtilizator(ActionEvent actionEvent) throws IOException {
        var stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/guiex1/views/AddView.fxml"));

        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));

        AddUserController userController = fxmlLoader.getController();
        userController.setUtilizatorService(service, tableView.getSelectionModel().getSelectedItem());

        stage.setWidth(400);
        stage.setHeight(200);
        stage.show();
    }

    public void openAddUserMenu(ActionEvent actionEvent) throws IOException {
        var stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/guiex1/views/AddView.fxml"));

        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));

        AddUserController userController = fxmlLoader.getController();
        userController.setUtilizatorService(service, new Utilizator("", ""));

        stage.setWidth(400);
        stage.setHeight(200);
        stage.show();
    }
}
