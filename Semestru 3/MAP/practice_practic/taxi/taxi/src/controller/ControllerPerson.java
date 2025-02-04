package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.Service;
import utils.EventChange;
import utils.Observer;
import domain.Chauffer;
import domain.Person;

public class ControllerPerson implements Observer<EventChange>{
    private Service Service;
    private Person Person;
    private Chauffer SelectedChauffer = null;

    @FXML
    private VBox mainVBox;
    @FXML
    private TableView<Chauffer> tableView;
    @FXML
    private TableColumn<Chauffer, String> nameColumn, carColumn;
    @FXML
    private TextField searchTextField = new TextField();
    @FXML
    private Button searchButton = new Button("Search");
    @FXML 
    private Button orderButton = new Button("Order");

    ObservableList<Chauffer> model = FXCollections.observableArrayList();

    public void setService(Service service, Person person) {
        this.Service = service;
        this.Service.addObserver(this);
        this.Person = person;
        this.model.setAll(this.Service.getChauffers());
        this.initElements();
    }

    public void initElements() {
        this.searchTextField.setPromptText("Search for a car");
        this.mainVBox.getChildren().add(new Label("Client " + this.Person.getName()));
        this.searchButton.setOnAction(e -> {
            this.model.setAll(this.Service.getChauffers().stream().filter(c -> c.getCar().contains(this.searchTextField.getText())).toList());
        });
        this.orderButton.setOnAction(e -> {
            if (this.SelectedChauffer == null) {
                return;
            }
            this.Service.addRequest(this.Person, this.SelectedChauffer);;
        });
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.carColumn.setCellValueFactory(new PropertyValueFactory<>("car"));
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            this.SelectedChauffer = newSelection;
        });
        this.tableView.setItems(this.model);
        this.mainVBox.getChildren().add(new HBox(this.searchTextField, this.searchButton, this.orderButton));
    }

    @Override
    public void update(EventChange e) {
    }
}
