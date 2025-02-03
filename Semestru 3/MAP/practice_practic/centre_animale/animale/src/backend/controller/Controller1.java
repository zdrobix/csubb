package backend.controller;


import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


import backend.domain.*;
import backend.service.Service;
import backend.utils.EventChange;
import backend.utils.Observer;

public class Controller1 implements Observer<EventChange> { 
    private Service ServiceA;
    private AdoptionCenter Center;
    private Animal SelectedAnimal;

    ObservableList<Animal> model = FXCollections.observableArrayList();

    @FXML
    private Label centerLabel, addressLabel, occupancyLabel;
    @FXML 
    private TableView<Animal> animalTable;
    @FXML
    private TableColumn<Animal, String> animalName;
    @FXML
    private TableColumn<Animal, String> animalType;
    @FXML 
    private VBox mainVBox;

    public void setService(Service service, AdoptionCenter center) {
        ServiceA = service;
        ServiceA.addObserver(this); 
        this.Center = center;
        this.model.setAll(ServiceA.getAnimalsFromCenter(this.Center.getID()));
        this.initialize2();
    }

    public void initialize2() {
        this.centerLabel.setText("Center: " + Center.getName());
        this.addressLabel.setText("Address: " + Center.getAddress());
        this.occupancyLabel.setText("Occupancy: " + this.ServiceA.getAnimalsFromCenter(this.Center.getID()).size() * 1.0 /this.Center.getCapacity() * 100 + "% full");
        this.animalName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.animalType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        this.animalTable.setItems(model);
        this.animalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.SelectedAnimal = newSelection;
                System.out.println(this.SelectedAnimal.getName());
            }
        });
        var animalTypeCombo = new ComboBox<String>();
        animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        animalTypeCombo.getItems().addAll("DOG", "CAT", "BIRD", "RODENT", "FISH", "ALL");
        animalTypeCombo.getSelectionModel().select("ALL");
        animalTypeCombo.setOnAction(e -> {
            if (animalTypeCombo.getValue().equals("ALL")) 
                this.model.setAll(ServiceA.getAnimalsFromCenter(this.Center.getID()));
            else 
                this.model.setAll(ServiceA.getAnimalsOfTypeFromCenter(AnimalType.valueOf(animalTypeCombo.getValue()), this.Center.getID()));
            
        });
        animalTypeCombo.setPrefWidth(250);
        this.mainVBox.getChildren().add(animalTypeCombo);
        var button = new Button("Request");
        button.setOnAction(e -> {
            ServiceA.InitializeTransfer(this.SelectedAnimal);
        });
        button.setPrefWidth(250);
        this.mainVBox.getChildren().add(button);
    }

    @Override
    public void update(EventChange e) {
        switch (e.getType()) {
            case "transfer":
            {
                if (this.Center.getID() == e.getData().getCenterID()) {
                    this.model.add(e.getData());
                }
                if (this.Center.getID() == e.getOldData().getCenterID()) {
                    this.model.remove(e.getOldData());
                }
                this.occupancyLabel.setText("Occupancy: " + this.ServiceA.getAnimalsFromCenter(this.Center.getID()).size() * 1.0 /this.Center.getCapacity() * 100 + "% full");
                break;
            }
            case "request":
            {
                if (this.Center.getID() != e.getData().getCenterID()) {
                     var alert = new Alert(Alert.AlertType.CONFIRMATION);
                     alert.setTitle("Transfer Request");
                     alert.setHeaderText("Transfer Request");
                     alert.setContentText("Do you want to transfer " + e.getData().getName() + " to " + this.Center.getName() + "?");
                     var result = alert.showAndWait();
                     if (result.get() == ButtonType.OK) {
                        this.ServiceA.TransferAnimal(e.getData(), Center);
                     }
                break;
              }
            }
        }
    }
}
