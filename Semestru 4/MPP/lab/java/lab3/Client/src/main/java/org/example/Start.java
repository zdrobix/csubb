package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controller.Controller;
import org.example.protocol.Proxy;
import org.example.services.IService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class Start extends Application {
    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";

    private static Logger logger = LogManager.getLogger(Start.class);

    public void start(Stage primaryStage) throws Exception {
        run_client();
    }

    public void run_client() throws Exception {
        logger.debug("Starting client application");
        Properties clientProps = new Properties();
        try{
            clientProps.load(Start.class.getResourceAsStream("/client.properties"));
            logger.info("Client properties set {}", clientProps);
        } catch (IOException e){
            logger.error("Cannot find client.properties {}", e);
            logger.debug("Looking into folder {}", new File(".").getAbsolutePath());
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;

        try{
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException e){
            logger.error("Wrong port number {}", clientProps.getProperty("server.port"));
            logger.debug("Using default port {}", defaultPort);
        }
        logger.info("Connecting to server {} on port {}", serverIP, serverPort);

//        IService server = new Proxy(serverIP, serverPort);
        IService server = new GrpcClient(serverIP, serverPort);

        URL fxmlUrl = getClass().getResource("/views/main_view.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);

        Controller controller = new Controller();
        controller.SetController(server);
        controller.SetKey(clientProps.getProperty("key.password"));

        loader.setController(controller);

        controller.StartApp();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
