package backend;

import java.io.IOException;

import backend.repo.RepoCenterAnimal;
import backend.service.Service;
import backend.controller.Controller1;
import backend.domain.AdoptionCenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var service = new Service(new RepoCenterAnimal());
        for (var center : service.getCenters()) {
            var stage = new Stage();
            initView(stage, service, center);
            stage.setWidth(800);
            stage.show();
        }
    }

    private void initView(Stage primaryStage, Service service, AdoptionCenter center) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/view-center.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        ((Controller1) fxmlLoader.getController()).setService(service, center);
    }
}
