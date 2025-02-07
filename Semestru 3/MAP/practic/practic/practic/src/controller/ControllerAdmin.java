package controller;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import service.Service;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class ControllerAdmin {
    private Service Service;

    @FXML 
    private VBox mainVBox;
    @FXML 
    private TextField nameTextField, durationTextField, datetimeTextField, seatNumberTextField;
    @FXML
    private DatePicker datePicker;

    public void setService(Service service) {
        this.Service = service;
        this.initElements();
    }
    

    private void initElements () {
        this.nameTextField = new TextField();
        nameTextField.setPromptText("Name");
        nameTextField.setMaxWidth(100);
        this.durationTextField = new TextField();
        durationTextField.setPromptText("Duration");
        durationTextField.setMaxWidth(100);
        this.seatNumberTextField = new TextField();
        seatNumberTextField.setPromptText("Seats");
        seatNumberTextField.setMaxWidth(100);
        this.datetimeTextField = new TextField();
        datetimeTextField.setPromptText("Date");
        datetimeTextField.setMaxWidth(100);

        var button = new Button("Add Show");
        button.setPrefWidth(100);
        button.setOnAction(e -> {
            var dateSplit = this.datetimeTextField.getText().split(":");
            var show = this.Service.addShow(
                this.nameTextField.getText(),
                Integer.parseInt(this.durationTextField.getText()),
                LocalDateTime.of(
                    Integer.parseInt(dateSplit[0]), 
                    Integer.parseInt(dateSplit[1]), 
                    Integer.parseInt(dateSplit[2]), 
                    Integer.parseInt(dateSplit[3]), 
                    Integer.parseInt(dateSplit[4]), 0), 
                Integer.parseInt(this.seatNumberTextField.getText()));
            if ( show != null) {
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("The show cannot be scheduled at the specified time as " + show.getName() + " will be playing at that time");
                alert.setTitle("Cannot add show");
                alert.show();
            }

        });

        this.mainVBox.getChildren().addAll(this.nameTextField, this.durationTextField, this.datetimeTextField, this.seatNumberTextField, button);
    }
}
