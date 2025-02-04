package controller;

import java.beans.EventHandler;
import java.io.IOException;

import domain.Chauffer;
import domain.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.Service;

public class ControllerLogin {
    private Service Service;

    @FXML
    private VBox mainVBox;
    @FXML
    private TextField usernameTextField;

    public void setService(Service service) {
        this.Service = service;
        this.initElements();
    }
    

    private void initElements () {
        this.usernameTextField = new TextField();
        usernameTextField.setPromptText("Username");
        usernameTextField.setPrefWidth(100);

        var button = new Button("Login");
        button.setOnAction(e -> {
            if (usernameTextField.getText().isEmpty()) {
                return;
            }
            Chauffer chauffer = this.Service.getChauffers().stream().filter(ch -> ch.getUsername().equals(usernameTextField.getText())).findFirst().orElse(null);
            if (chauffer != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/view-chauffer.fxml"));
                var stage = new Stage();
                try {
                    stage.setScene(new Scene(fxmlLoader.load()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ((ControllerChauffer)fxmlLoader.getController()).setService(this.Service, chauffer);
                stage.show();
                return;
            }
            Person person = this.Service.getPersons().stream().filter(p -> p.getUsername().equals(usernameTextField.getText())).findFirst().orElse(null);
            if (person != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/view-person.fxml"));
                var stage = new Stage();
                try {
                    stage.setScene(new Scene(fxmlLoader.load()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ((ControllerPerson)fxmlLoader.getController()).setService(this.Service, person);
                stage.show();
                return;
            }
        });
        button.setPrefWidth(100);

        this.mainVBox.getChildren().addAll(usernameTextField, button);
    }

}
