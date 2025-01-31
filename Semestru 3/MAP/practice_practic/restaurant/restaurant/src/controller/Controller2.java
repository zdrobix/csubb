package controller;


import java.util.ArrayList;
import java.util.List;

import domain.MenuItem;
import domain.Table;
import service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class Controller2 {
    private Service ServiceA;
    private Table TableA;
    ObservableList<MenuItem> model = FXCollections.observableArrayList();

    private List<Integer> OrderItems = new ArrayList<>();

    @FXML
    private VBox menuVBox;

    @FXML
    private Button placeOrder, clearOrder;

    public void setService(Service service, Table table) {
        this.ServiceA = service;
        this.TableA = table;
        var menu = this.ServiceA.GetMenu();
        menu.sort((a, b) -> a.getCategory().compareTo(b.getCategory()));
        this.model.setAll(menu);
        this.initialize2();
        this.placeOrder.setOnAction(this::handleOrderButton);
        System.out.println(table.getID());
        this.clearOrder.setOnAction(e -> {
            this.OrderItems.clear();
        });
    }

    public void initialize2() {
        String lastCategory = "";
        for ( var item : this.ServiceA.GetMenu()) {
            if (!item.getCategory().equals(lastCategory)) 
            {
                lastCategory = item.getCategory();
                var categoryLabel = new Label(item.getCategory());
                categoryLabel.setStyle("-fx-font-weight: bold;");
                this.menuVBox.getChildren().add(categoryLabel);
            } 

            var itemHbox = new HBox(30);
            var spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            var button = new Button("Add");
            button.setOnAction(e -> {
                this.OrderItems.add(item.getID());
            });
            itemHbox.getChildren().addAll(new Label(item.getItem() + " " + item.getPrice() + " " + item.getCurency()), spacer, button);
            this.menuVBox.getChildren().add(
                itemHbox
            );
        }
    }

    @FXML
    public void handleOrderButton (ActionEvent actionEvent) {
        if (this.OrderItems.size() != 0) {
            this.ServiceA.AddOrder(this.TableA.getID(), this.OrderItems);
        }
    }
}
