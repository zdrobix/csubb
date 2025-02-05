import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repo.Repository;

import service.Service;
import controller.ControllerLogin;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        var service = new Service(new Repository());
        initView(primaryStage, service);
        primaryStage.show();
    }

    private void initView(Stage primaryStage, Service service) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/view-login.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        ((ControllerLogin)fxmlLoader.getController()).setService(service);
    }
}