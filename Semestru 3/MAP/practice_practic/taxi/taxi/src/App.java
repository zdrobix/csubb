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


//compile
///javac --module-path "Q:\kit\JavaFX\javafx-sdk-21.0.6\lib" --add-modules javafx.controls,javafx.fxml -d bin src/template/controller/*.java src/template/domain/*.java src/template/repo/*.java src/template/service/*.java src/template/utils/*.java src/template/App.java
//run
///java --module-path "Q:\kit\JavaFX\javafx-sdk-21.0.6\lib" --add-modules javafx.controls,javafx.fxml -cp "bin;Q:/Downloads/postgresql-42.7.4.jar" template.App