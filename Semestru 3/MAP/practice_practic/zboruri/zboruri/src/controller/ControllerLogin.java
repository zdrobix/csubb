package controller;

import java.io.IOException;

import domain.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
        usernameTextField.setMaxWidth(100);

        var button = new Button("Login");
        button.setOnAction(e -> {
            if (this.usernameTextField.getText().isEmpty())
                return;
            Client client = this.Service.getClients().stream().filter(c -> c.getUsername().equals(this.usernameTextField.getText().strip())).findFirst().orElse(null);
            if (client == null)
                return;
            System.out.println("Pressed./");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/view-client.fxml"));
            var stage = new Stage();
                try {
                    stage.setScene(new Scene(fxmlLoader.load()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ((ControllerClient)fxmlLoader.getController()).setService(this.Service);
                stage.show();
        });
        button.setPrefWidth(100);

        this.mainVBox.getChildren().addAll(usernameTextField, button);
    }
}
