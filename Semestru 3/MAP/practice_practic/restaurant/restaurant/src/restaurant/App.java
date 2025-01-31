package restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repo.RepoMenu;
import repo.RepoOrders;
import repo.RepoTables;
import service.Service;

import java.io.IOException;

import controller.Controller1;

public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/view-1.fxml"));

        primaryStage.setScene(new Scene(fxmlLoader.load()));
        ((Controller1)fxmlLoader.getController()).setService(new Service(new RepoOrders(), new RepoMenu(), new RepoTables()));
    }
}
