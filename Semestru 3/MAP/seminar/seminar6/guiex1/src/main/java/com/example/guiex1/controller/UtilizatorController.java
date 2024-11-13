package main.java.com.example.guiex1.controller;

import main.java.com.example.guiex1.domain.Utilizator;
import main.java.com.example.guiex1.domain.ValidationException;
import main.java.com.example.guiex1.services.UtilizatorService;
import main.java.com.example.guiex1.utils.events.UtilizatorEntityChangeEvent;
import main.java.com.example.guiex1.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;




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
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,String> tableColumnFirstName;
    @FXML
    TableColumn<Utilizator,String> tableColumnLastName;


    public void setUtilizatorService(UtilizatorService service) {
        this.service = service;
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

    }

    public void handleDeleteUtilizator(ActionEvent actionEvent) {
        Utilizator user=(Utilizator) tableView.getSelectionModel().getSelectedItem();
        if (user!=null) {
            Utilizator deleted= service.deleteUtilizator(user.getId());
        }
    }

    public void handleAddUtilizator(ActionEvent actionEvent) {
    }

    public void handleUpdateUtilizator(ActionEvent actionEvent) {
        Utilizator selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String newFirstName = firstNameTextField.getText();
            String newLastName = lastNameTextField.getText();

            selectedUser.setFirstName(newFirstName);
            selectedUser.setLastName(newLastName);

            // Update user in the service
            service.updateUtilizator(selectedUser);
        }
    }
}
