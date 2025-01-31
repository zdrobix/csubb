package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

import domain.*;
import service.Service;
import restaurant.App;
import observer.*;

public class Controller1 implements Observer<OrderAddedEvent> { 
    private Service ServiceA;
    ObservableList<Order> model = FXCollections.observableArrayList();

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, Integer> orderTableNumber;
    @FXML
    private TableColumn<Order, LocalDateTime> orderDate;
    @FXML
    private TableColumn<Order, String> orderItems;

    @FXML
    private HBox tableHBox;

    public void setService(Service service) {
        this.ServiceA = service;
        ServiceA.addObserver(this);
        var orders = this.ServiceA.GetOrders();
        orders.sort((a, b) -> a.getDate().compareTo(b.getDate()));
        this.model.setAll(orders);
        this.initialize2();
    }

    public void initialize2() {
        orderTableNumber.setCellValueFactory(new PropertyValueFactory<>("TableID"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        orderItems.setCellValueFactory( data -> {
            var items = data.getValue().getItemsIDs();
            var menu = this.ServiceA.GetMenu();
            var itemsString = "";
            for (var item : items) {
                var menuItem = menu.stream().filter(x -> x.getID() == item).findFirst().get();
                itemsString += menuItem.getItem() + ", ";
            }
            return new javafx.beans.property.SimpleStringProperty(itemsString.substring(0, itemsString.length() - 2));
        });
        ordersTable.setItems(model);

        for (var table : this.ServiceA.GetTables()) {
            var button = new Button("" + table.getID());
            button.setOnAction(e -> {
                var stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/view-2.fxml"));
                try {
                    stage.setScene(new javafx.scene.Scene(fxmlLoader.load()));
                    ((Controller2)fxmlLoader.getController()).setService(this.ServiceA, table);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            this.tableHBox.getChildren().add(button);
        }
    }

    @Override
    public void update(OrderAddedEvent e) {
        this.model.add(e.getData());
    }
}
