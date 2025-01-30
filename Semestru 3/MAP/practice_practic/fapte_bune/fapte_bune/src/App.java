import java.io.IOException;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import repo.RepositoryNeeds;
import repo.RepositoryPersons;
import service.Service;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Controller controller = fxmlLoader.getController();
        controller.setService(
            new Service(new RepositoryNeeds(), new RepositoryPersons())
        );
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
