package controller;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import domain.Show;
import domain.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.Service;
import utils.Observer;
import utils.EventChange;

public class ControllerUser implements Observer<EventChange>{
    private Service Service;
    private User User;

    @FXML
    private VBox mainVBox;
    @FXML
    private TableView<Show> tableView;
    @FXML
    private TableColumn<Show, String> nameColumn, dateColumn;
    @FXML
    private TableColumn<Show, Integer> seatColumn;
    @FXML 
    private Button reserveButton = new Button("Reserve");

    ObservableList<Show> model = FXCollections.observableArrayList();
    private int pageNr = 0;
    private int selectedShow;
    private boolean canRecieve = true;


    public void setService(Service service, User user) {
        this.Service = service;
        this.Service.addObserver(this);
        this.User = user;
        this.model.setAll(this.Service.getShowsOnPage(0));
        this.initElements();
    }
    

    private void initElements () {
        this.mainVBox.getChildren().add(new Label(this.User.getEmail()));
        this.nameColumn.setCellValueFactory( data -> new SimpleStringProperty(data.getValue().getName()));
        this.dateColumn.setCellValueFactory( data -> {
            var string =  data.getValue().getStartDate().format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm")).toString();
            return new SimpleStringProperty(string);
        });
        this.seatColumn.setCellValueFactory( data -> {
            return new SimpleIntegerProperty((int) (data.getValue().getNumberSeats() - this.Service.getSeats().stream().filter(x -> x.getShowId() == data.getValue().getId()).count())).asObject();
        });
        this.tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null)
                selectedShow = -1;
            this.selectedShow = newSelection.getId();
            var reservations = this.Service.getSeats().stream().filter(x -> x.getEmail() == User.getEmail()).collect(Collectors.toList());
            if (reservations.stream().filter(r -> r.getShowId() == this.selectedShow).count() != 0)
                this.reserveButton.setVisible(false);
        });
        this.tableView.setItems(this.model);
        var hbox = new HBox();
        var next = new Button("Next");
        next.setOnAction(
            e -> {
                if (pageNr + 1 > this.Service.getNumberOfShows())
                    return;
                this.model.setAll(
                    this.Service.getShowsOnPage(pageNr ++)
                );
                this.tableView.setItems(this.model);
            }
        );
        var prev = new Button("Prev");
        prev.setOnAction(
            e -> {
                if (pageNr - 1 < 0)
                    return;
                this.model.setAll(
                    this.Service.getShowsOnPage(pageNr --)
                );
                this.tableView.setItems(this.model);
            }
        );
        hbox.getChildren().addAll(prev, next);
        this.mainVBox.getChildren().add(hbox);
        reserveButton.setOnAction(e -> {
            if (selectedShow == -1) 
                return;
            this.Service.addReservation(
                this.selectedShow,
                this.User
            );
        });
        this.mainVBox.getChildren().add(reserveButton);
    }


    @Override
    public void update(EventChange e) {
        switch (e.getType()) {
            case "addshow":
                if (!canRecieve)
                    return;
                var alert = new Alert(Alert.AlertType.CONFIRMATION);
                var show = (Show)e.getData();
                alert.setTitle("A new show has been added!");
                alert.setContentText("Do you want to see it?");
                var result = alert.showAndWait();
                canRecieve = false;
                if (result.get() == ButtonType.OK) {
                    this.Service.addReservation(show.getId(), this.User);
                    canRecieve = true;
                }
            case "addreservation": {
                System.out.println("called update function");
                this.seatColumn.setCellValueFactory( data -> {
                    return new SimpleIntegerProperty((int) (data.getValue().getNumberSeats() - this.Service.getSeats().stream().filter(x -> x.getShowId() == data.getValue().getId()).count())).asObject();
                });
            }
            default:
                break;
        }
    }
}
