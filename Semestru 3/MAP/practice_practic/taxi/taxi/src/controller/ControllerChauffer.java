package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.Service;
import utils.EventChange;
import utils.Observer;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import domain.Chauffer;
import domain.Order;
import domain.Person;

public class ControllerChauffer implements Observer<EventChange>{
    private Service Service;
    private Chauffer Chauffer;

    @FXML
    private VBox mainVBox;
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, String> nameColumn, dateColumn;
    @FXML 
    private DatePicker datePicker = new DatePicker();
    @FXML 
    private Label meanLabel = new Label(), favLabel = new Label();

    ObservableList<Order> model = FXCollections.observableArrayList();

    public void setService(Service service, Chauffer chauffer) {
        this.Service = service;
        this.Chauffer = chauffer;
        this.Service.addObserver(this);
        this.model.setAll(this.Service.getOrders().stream().filter(x -> x.getChauffer().getId() == this.Chauffer.getId()).toList());
        this.initElements();
    }

    public void initElements() {
        this.mainVBox.getChildren().add(new Label ("Chauffer " + this.Chauffer.getName()));
        this.datePicker.setOnAction(e -> {
            this.model.setAll(this.Service.getOrders().stream().filter(x -> x.getChauffer().getId() == this.Chauffer.getId() && x.getDate().toLocalDate().equals(this.datePicker.getValue())).toList());
        });
        this.mainVBox.getChildren().add(datePicker);
        var names = this.Service.getOrders().stream().map(order -> order.getPerson().getName()).distinct().toList();
        names.forEach(name -> {
            this.mainVBox.getChildren().add(new Label(name));
        });
        this.meanLabel.setText("Mean for the last 3 months is: " + this.Service.getOrders().stream().filter(order -> {
            return order.getChauffer().getId() == this.Chauffer.getId() && order.getDate().toLocalDate().isAfter(LocalDate.now().minusMonths(3));
        }).count() * 1.0 / 90);
        var favouriteClient = this.Service.getOrders().stream()
            .filter(order -> order.getChauffer().getId() == this.Chauffer.getId())
            .map(Order::getPerson)
            .collect(Collectors.groupingBy(Person::getName, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("No clients");
        this.favLabel.setText("Favourite client: " + favouriteClient);
        this.mainVBox.getChildren().addAll(meanLabel, favLabel);
        this.nameColumn.setCellValueFactory( data -> new SimpleStringProperty(data.getValue().getPerson().getName()));
        this.dateColumn.setCellValueFactory( data -> new SimpleStringProperty(data.getValue().getDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        this.tableView.setItems(model);
    }

    @Override
    public void update(EventChange e) {
        switch (e.getType()) {
            case "order": {
                if (e.getData().getChauffer().getId() != this.Chauffer.getId()) {
                    return;
                }
                var alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Order for " + ((Order)e.getData()).getPerson().getName());
                alert.setHeaderText("Order requested");
                alert.setContentText("Do you want to accept the order?");
                var result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    this.Service.addOrder(((Order)e.getData()).getPerson(), e.getData().getChauffer());
                }
                break;
            }
            case "add": {
                if (e.getData().getChauffer().getId() != this.Chauffer.getId()) {
                    return;
                }
                this.model.add(e.getData());
                this.meanLabel.setText("Mean for the last 3 months is: " + this.Service.getOrders().stream().filter(order -> {
                    return order.getChauffer().getId() == this.Chauffer.getId() && order.getDate().toLocalDate().isAfter(LocalDate.now().minusMonths(3));
                }).count() * 1.0 / 90);
                var favouriteClient = this.Service.getOrders().stream()
                    .filter(order -> order.getChauffer().getId() == this.Chauffer.getId())
                    .map(Order::getPerson)
                    .collect(Collectors.groupingBy(Person::getName, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("No clients");
                this.favLabel.setText("Favourite client: " + favouriteClient);
                break;
            }
        }
    }
}
