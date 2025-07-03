package org.example.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.domain.Child;
import org.example.domain.Event;
import org.example.domain.LoginInfo;
import org.example.domain.Signup;
import org.example.services.*;
import org.example.utils.Crypter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Controller implements IObserver {
    private LoginInfo CurrentLogin;
    private static IService Service;

    private static String Key;

    private static IServiceChild ServiceChild;
    private static IServiceSignup ServiceSignup;
    private static IServiceLogin ServiceLogin;
    private static IServiceEvent ServiceEvent;

    private static int SelectedChildId = -1;
    private static int SelectedEventId = -1;

    @FXML
    private static TextField childField = new TextField(), eventField = new TextField();

    private static ObservableList<Child> modelChild;
    private static ObservableList<Event> modelEvent;
    private static ObservableList<Signup> modelSignup;

    private int LastNotifiedChildId = -1;
    private int LastNotifiedEventId = -1;

    public Controller() {};

    public void SetController(IService service) {
        Service = service;
        //ServiceChild = service.getServiceChild();
        //ServiceSignup = service.getServiceSignup();
        //ServiceLogin = service.getServiceLogin();
        //ServiceEvent = service.getServiceEvent();
    }

    public void SetKey(String key) {
        Key = key;
    }

    public void StartApp () {
        VBox vbox = CreateLoginVBox();

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(vbox);

        Stage loginStage = new Stage();
        Scene MainScene = new Scene(stackPane, 800, 600);
        MainScene.getStylesheets().add(Controller.class.getResource("/css/styles.css").toExternalForm());
        loginStage.setTitle("Login Page");
        loginStage.setScene(MainScene);
        loginStage.show();
    }

    public VBox CreateLoginVBox () {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label("Children events");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);

        TextField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        //REMOVE
        usernameField.setText("alex");
        passwordField.setText("parola");

        Button buttonLogin = new Button("Login");
        buttonLogin.setMaxWidth(200);
        buttonLogin.setOnAction(e -> {
            String username = usernameField.getText().strip();
            String password = passwordField.getText().strip();

            String encrypted;
            try {
                encrypted = Crypter.encrypt(
                        password,
                        Key
                );
            } catch (Exception ex) {
                showAlert("Invalid file password.", Alert.AlertType.ERROR);
                return;
            }
            LoginInfo login = Service.GetByUsername(username);
            System.out.println(login);
            if (login.GetPassword().equals(encrypted)) {
                if (!Service.login(login, this))
                    return;
                this.CurrentLogin = login;
                showMenuPage();
                Stage stage = (Stage) buttonLogin.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Invalid password.", Alert.AlertType.ERROR);
            }
        });

        vbox.getChildren().addAll(label, usernameField, passwordField, buttonLogin);
        return vbox;
    }

    private void showMenuPage() {
        VBox menuVBox = createMenuVBox();

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(menuVBox);

        Stage menuStage = new Stage();
        menuStage.setOnCloseRequest(event -> {
            if (this.CurrentLogin != null)
                Service.logout(this.CurrentLogin, this);
        });
        Scene menuScene = new Scene(stackPane, 800, 600);
        menuScene.getStylesheets().add(Controller.class.getResource("/css/styles.css").toExternalForm());
        menuStage.setTitle("Menu Page");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    private VBox createMenuVBox() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        var childMenu = createChildMenu();
        var eventMenu = createEventMenu();
        var signupMenu = createSignupMenu();
        var refresh = new Button("Refresh");
        refresh.setPrefWidth(200);
        refresh.setOnAction(e -> {
            showMenuPage();
            Stage stage = (Stage) vbox.getScene().getWindow();
            stage.close();
        });
        var logout = new Button("Logout");
        logout.setPrefWidth(200);
        logout.setOnAction(e -> {
            try {
                Service.logout(this.CurrentLogin, this);
                StartApp();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            Stage stage = (Stage) vbox.getScene().getWindow();
            stage.close();
        });

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(childMenu,eventMenu, signupMenu);
        vbox.getChildren().addAll(hbox, refresh, logout);

        return vbox;
    }

    private VBox createChildMenu() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        modelChild = FXCollections.observableArrayList();
        modelChild.setAll((Collection<? extends Child>) Service.GetAllChildren());
        /*
        Task<Iterable<Child>> task = new Task<>() {
            @Override
            protected Iterable<Child> call() throws Exception {
                return Service.GetAllChildren();
            }
        };

        task.setOnSucceeded(e -> {
            modelChild.setAll((Collection<? extends Child>) task.getValue());
        });
        new Thread(task).start();
        */
        TableView<Child> childTable = new TableView<>();
        TableColumn<Child, String> childIdColumn = new TableColumn<>("Id");
        TableColumn<Child, String> childNameColumn = new TableColumn<>("Name");
        TableColumn<Child, String> childCnpColumn = new TableColumn<>("CNP");

        childIdColumn.setPrefWidth(20);
        childIdColumn.setVisible(false);
        childNameColumn.setPrefWidth(100);
        childCnpColumn.setPrefWidth(100);
        childTable.setMaxWidth(200);
        childTable.setMaxHeight(200);

        childIdColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("" + cellData.getValue().GetId());
        });
        childNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().GetName());
        });
        childCnpColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().GetCNP());
        });

        childTable.getColumns().addAll(childIdColumn, childNameColumn, childCnpColumn);
        childTable.setItems(modelChild);

        HBox searchName = new HBox(10);

        TextField searchNameField = new TextField();
        searchNameField.setPromptText("Name");
        searchNameField.setMaxWidth(100);

        Button searchButton = new Button("Search");
        searchButton.setPrefWidth(90);
        searchButton.setOnAction(e -> {
            if (searchNameField.getText().isEmpty()) {
                modelChild.setAll((Collection<Child>)Service.GetAllChildren());
                return;
            }
            var searchResult = ((Collection<Child>) Service.GetAllChildren())
                    .stream()
                    .filter(
                            x ->
                                    x.GetName()
                                            .toLowerCase()
                                            .contains(
                                                    searchNameField
                                                            .getText()
                                                            .toLowerCase()
                                                            .strip()
                                            )
                    )
                    .collect(Collectors.toList());
            modelChild.setAll(
                    searchResult
            );
        });

        searchName.getChildren().addAll(searchNameField, searchButton);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setMaxWidth(200);

        TextField cnpField = new TextField();
        cnpField.setPromptText("Cnp");
        cnpField.setMaxWidth(200);

        childTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SelectedChildId = newValue.GetId();
                childField.setText("" + SelectedChildId);
                nameField.setText(newValue.GetName());
                cnpField.setText(newValue.GetCNP());
            }
        });

        Button addChildButton = new Button("Add");
        addChildButton.setPrefWidth(200);
        addChildButton.setOnAction(e -> {
            if (nameField.getText().isEmpty() || cnpField.getText().isEmpty() || cnpField.getText().length() < 6) {
                showAlert("Invalid name or cnp.", Alert.AlertType.ERROR);
                return;
            }
            Service.AddChild(nameField.getText(), cnpField.getText());
            nameField.clear();
            cnpField.clear();
        });

        Button updateChild = new Button("Update");
        updateChild.setPrefWidth(200);
        updateChild.setOnAction(e -> {
            if (nameField.getText().isEmpty() || cnpField.getText().isEmpty() || cnpField.getText().length() < 6) {
                showAlert("Invalid name or cnp.", Alert.AlertType.ERROR);
                return;
            }
            if (SelectedChildId == -1) {
                showAlert("Child not selected.", Alert.AlertType.ERROR);
                return;
            }
            var oldChild = Service.GetChildById(SelectedChildId);
            var newChild = Service.UpdateChild((Child)new Child(nameField.getText(), cnpField.getText()).SetId(SelectedChildId));
            nameField.clear();
            cnpField.clear();
            //modelChild.set(modelChild.indexOf(oldChild), newChild);
        });

        vbox.getChildren().addAll(searchName, childTable, nameField, cnpField, addChildButton, updateChild);

        return vbox;
    }

    private VBox createEventMenu() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        modelEvent = FXCollections.observableArrayList();
        modelEvent.setAll((Collection<? extends Event>) Service.GetAllEvents());
        /*
        Task<Iterable<Event>> task = new Task<>() {
            @Override
            protected Iterable<Event> call() throws Exception {
                return Service.GetAllEvents();
            }
        };

        task.setOnSucceeded(e -> {
            modelEvent.setAll((Collection<? extends Event>) task.getValue());
        });
        new Thread(task).start();
         */

        TableView<Event> eventTable = new TableView<>();
        TableColumn<Event, String> eventIdColumn = new TableColumn<>("Id");
        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Name");
        TableColumn<Event, String> eventAgeColumn = new TableColumn<>("Age Interval");

        eventIdColumn.setPrefWidth(20);
        eventIdColumn.setVisible(false);
        eventNameColumn.setPrefWidth(100);
        eventAgeColumn.setPrefWidth(100);
        eventTable.setMaxWidth(200);
        eventTable.setMaxHeight(200);

        eventIdColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("" + cellData.getValue().GetId());
        });
        eventNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().GetName());
        });
        eventAgeColumn.setCellValueFactory(cellData -> {
            var data = cellData.getValue();
            return new SimpleStringProperty("" + data.GetMinAge() + "-" + data.GetMaxAge());
        });

        eventTable.getColumns().addAll(eventIdColumn, eventNameColumn, eventAgeColumn);
        eventTable.setItems(modelEvent);

        HBox searchName = new HBox(10);

        TextField searchNameField = new TextField();
        searchNameField.setPromptText("Name");
        searchNameField.setMaxWidth(100);

        Button searchButton = new Button("Search");
        searchButton.setPrefWidth(90);
        searchButton.setOnAction(e -> {
            if (searchNameField.getText().isEmpty()) {
                modelEvent.setAll((Collection<Event>) Service.GetAllEvents());
                return;
            }
            var searchResult = ((Collection<Event>) Service.GetAllEvents())
                    .stream()
                    .filter(
                            x ->
                                    x.GetName()
                                            .toLowerCase()
                                            .contains(
                                                    searchNameField
                                                            .getText()
                                                            .toLowerCase()
                                                            .strip()
                                            )
                    )
                    .collect(Collectors.toList());
            modelEvent.setAll(
                    searchResult
            );
        });

        searchName.getChildren().addAll(searchNameField, searchButton);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setMaxWidth(200);

        TextField minAgeField = new TextField();
        minAgeField.setPromptText("Min");
        minAgeField.setMaxWidth(90);

        TextField maxAgeField = new TextField();
        maxAgeField.setPromptText("Max");
        maxAgeField.setMaxWidth(90);

        eventTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SelectedEventId = newValue.GetId();
                eventField.setText("" + SelectedEventId);
                nameField.setText(newValue.GetName());
                minAgeField.setText("" + newValue.GetMinAge());
                maxAgeField.setText("" + newValue.GetMaxAge());
            }
        });

        HBox ageHBox = new HBox(20);
        ageHBox.setAlignment(Pos.CENTER);
        ageHBox.getChildren().addAll(minAgeField, maxAgeField);

        Button addEventButton = new Button("Add");
        addEventButton.setPrefWidth(200);
        addEventButton.setOnAction(e -> {
            if (nameField.getText().isEmpty() || minAgeField.getText().isEmpty() || maxAgeField.getText().isEmpty()) {
                showAlert("Empty name or age.", Alert.AlertType.ERROR);
                return;
            }
            int minAge = Integer.parseInt(minAgeField.getText());
            int maxAge = Integer.parseInt(maxAgeField.getText());
            if (minAge > maxAge) {
                showAlert("Invalid age.", Alert.AlertType.ERROR);
                return;
            }
            Service.AddEvent(nameField.getText(), minAge, maxAge);
            nameField.clear();
            minAgeField.clear();
            maxAgeField.clear();
        });

        Button updateEventButton = new Button("Update");
        updateEventButton.setPrefWidth(200);
        updateEventButton.setOnAction(e -> {
            if (nameField.getText().isEmpty() || minAgeField.getText().isEmpty() || maxAgeField.getText().isEmpty()) {
                showAlert("Invalid name or age.", Alert.AlertType.ERROR);
                return;
            }
            if (SelectedEventId == -1) {
                showAlert("Event not selected.", Alert.AlertType.ERROR);
                return;
            }
            int minAge, maxAge;
            try {
                minAge = Integer.parseInt(minAgeField.getText());
                maxAge = Integer.parseInt(maxAgeField.getText());
            } catch (NumberFormatException ex) {
                showAlert("Invalid age.", Alert.AlertType.ERROR);
                return;
            }
            Event oldEvent = Service.GetEventById(SelectedEventId);
            Event newEvent = Service.UpdateEvent((Event) new Event(nameField.getText(), minAge, maxAge).SetId(SelectedEventId));
            nameField.clear();
            minAgeField.clear();
            maxAgeField.clear();
        });

        vbox.getChildren().addAll(searchName, eventTable, nameField, ageHBox, addEventButton, updateEventButton);

        return vbox;
    }

    private VBox createSignupMenu() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        modelSignup = FXCollections.observableArrayList();
        modelSignup.setAll((Collection<? extends Signup>) Service.GetAllSignups());
        /*
        Task<Iterable<Signup>> task = new Task<>() {
            @Override
            protected Iterable<Signup> call() throws Exception {
                return Service.GetAllSignups();
            }
        };

        task.setOnSucceeded(e -> {
            modelSignup.setAll((Collection<? extends Signup>) task.getValue());
        });
        new Thread(task).start();
        */
        TableView<Signup> signupTable = new TableView<>();
        TableColumn<Signup, String> childColumn = new TableColumn<>("Child");
        TableColumn<Signup, String> eventColumn = new TableColumn<>("Event");

        childColumn.setPrefWidth(100);
        eventColumn.setPrefWidth(100);
        signupTable.setMaxWidth(200);
        signupTable.setMaxHeight(200);

        childColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().GetChild().GetName());
        });
        eventColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().GetEvent().GetName());
        });

        signupTable.getColumns().addAll(childColumn, eventColumn);
        signupTable.setItems(modelSignup);

        HBox searchName = new HBox(10);

        TextField searchIdField = new TextField();
        searchIdField.setPromptText("Id");
        searchIdField.setMaxWidth(40);

        Button searchChildIdButton = new Button("Child");
        searchChildIdButton.setPrefWidth(70);
        searchChildIdButton.setOnAction(e -> {
            if (searchIdField.getText().isEmpty()) {
                modelSignup.setAll((Collection<? extends Signup>) Service.GetAllSignups());
                return;
            }
            int id;
            try {
                id = Integer.parseInt(searchIdField.getText());
            } catch (NumberFormatException ex) {
                showAlert("Invalid id format.", Alert.AlertType.ERROR);
                return;
            }
            var searchResult = ((Collection<? extends Signup>) Service.GetAllSignups())
                    .stream()
                    .filter(
                            x ->
                                    x.GetChild().GetId() == id
                    )
                    .collect(Collectors.toList());
            modelSignup.setAll(
                    searchResult
            );
        });

        Button searchEventIdButton = new Button("Event");
        searchEventIdButton.setPrefWidth(70);
        searchEventIdButton.setOnAction(e -> {
            if (searchIdField.getText().isEmpty()) {
                modelSignup.setAll((Collection<? extends Signup>) Service.GetAllSignups());
                return;
            }
            int id;
            try {
                id = Integer.parseInt(searchIdField.getText());
            } catch (NumberFormatException ex) {
                showAlert("Invalid id format.", Alert.AlertType.ERROR);
                return;
            }
            var searchResult = ((Collection<? extends Signup>) Service.GetAllSignups())
                    .stream()
                    .filter(
                            x ->
                                    x.GetEvent().GetId() == id
                    )
                    .collect(Collectors.toList());
            modelSignup.setAll(
                    searchResult
            );
        });

        searchName.getChildren().addAll(searchIdField, searchChildIdButton, searchEventIdButton);

        childField.setEditable(false);
        childField.setMaxWidth(200);

        eventField.setEditable(false);
        eventField.setMaxWidth(200);

        Button addSignupButton = new Button("Add");
        addSignupButton.setPrefWidth(200);
        addSignupButton.setOnAction(e -> {
            if (childField.getText().isEmpty() || eventField.getText().isEmpty()) {
                showAlert("Invalid child or event.", Alert.AlertType.ERROR);
                return;
            }
            Service.AddSignup(
                    Service.GetChildById(Integer.parseInt(childField.getText())),
                    Service.GetEventById(Integer.parseInt(eventField.getText()))
            );
            childField.clear();
            eventField.clear();
        });

        Button deleteSignupButton = new Button("Delete");
        deleteSignupButton.setPrefWidth(200);
        deleteSignupButton.setOnAction(e -> {
            if (SelectedEventId == -1 || SelectedChildId == -1) {
                showAlert("Invalid child or event.", Alert.AlertType.ERROR);
                return;
            }
            Signup deleted = Service.DeleteSignup(SelectedChildId, SelectedEventId);
        });

        vbox.getChildren().addAll(searchName, signupTable, childField, eventField, addSignupButton, deleteSignupButton);

        return vbox;
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void childAdded(Child child) {
        if (this.LastNotifiedChildId == child.GetId())
            return;
        this.LastNotifiedChildId = child.GetId();
        Platform.runLater(() -> {
            System.out.println("Updating model.");
            modelChild.add(child);
        });
    }

    @Override
    public void eventAdded(Event event) {
        if (this.LastNotifiedEventId == event.GetId())
            return;
        this.LastNotifiedEventId = event.GetId();
        Platform.runLater(() -> {
            System.out.println("Updating model.");
            modelEvent.add(event);
        });
    }

    @Override
    public void signupAdded(Signup signup) {
        Platform.runLater(() -> {
            System.out.println("Updating model.");
            modelSignup.add(signup);
        });
    }
}
