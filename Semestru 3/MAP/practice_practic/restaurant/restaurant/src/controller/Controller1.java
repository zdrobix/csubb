package controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

import domain.*;
import service.Service;

public class Controller1 {
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

    public void setService(Service service) {
        this.ServiceA = service;
        var orders = this.ServiceA.GetOrders();
        orders.sort((a, b) -> a.getDate().compareTo(b.getDate()));
        this.model.setAll(orders);
    }

    @FXML
    public void initialize() {
        orderTableNumber.setCellValueFactory(new PropertyValueFactory<>("ID"));
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
    }
}
