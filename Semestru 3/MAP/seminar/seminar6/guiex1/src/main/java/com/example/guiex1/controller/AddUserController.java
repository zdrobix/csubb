package main.java.com.example.guiex1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.java.com.example.guiex1.domain.Utilizator;
import main.java.com.example.guiex1.domain.ValidationException;
import main.java.com.example.guiex1.services.UtilizatorService;

public class AddUserController {
    UtilizatorService service;

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField idTextField;

    Utilizator utilizator;

    public void setUtilizatorService(UtilizatorService service, Utilizator utilizator) {
        this.service = service;
        this.utilizator = utilizator;
        if (utilizator == null)
            return;
        firstNameTextField.setText(utilizator.getFirstName());
        lastNameTextField.setText(utilizator.getLastName());
    }

    public void handleAddUser(ActionEvent actionEvent) {
        try {
            var user =  new Utilizator(
                    this.firstNameTextField.getText().trim(),
                    this.lastNameTextField.getText().trim()
            );
            user.setId(
                    Long.parseLong(
                            this.idTextField.getText().trim()
                    )
            );
            service.addUtilizator(
                    user
            );
            this.firstNameTextField.clear();
            this.lastNameTextField.clear();
            this.idTextField.clear();
        } catch (ValidationException e) {
            //do something
        }
    }

    public void handleUpdateUser(ActionEvent actionEvent) {
        if (utilizator != null) {
            String newFirstName = firstNameTextField.getText();
            String newLastName = lastNameTextField.getText();

            utilizator.setFirstName(newFirstName);
            utilizator.setLastName(newLastName);

            // Update user in the service
            service.updateUtilizator(utilizator);
        }
    }
}
