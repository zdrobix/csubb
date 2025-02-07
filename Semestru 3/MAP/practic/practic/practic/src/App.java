import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repo.Repository;

import service.Service;
import controller.ControllerAdmin;
import controller.ControllerUser;
import domain.User;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        var service = new Service(new Repository());
        initView(primaryStage, service);
        for (var user : service.getUsers()) {
            var stage  = new Stage();
            this.initViewUser(stage, service, user);
            stage.show();
        }
        primaryStage.show();
    }

    private void initView(Stage primaryStage, Service service) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/view-admin.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        ((ControllerAdmin)fxmlLoader.getController()).setService(service);
    }

    private void initViewUser(Stage primaryStage, Service service, User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/view-user.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        ((ControllerUser)fxmlLoader.getController()).setService(service, user);
    }
}
