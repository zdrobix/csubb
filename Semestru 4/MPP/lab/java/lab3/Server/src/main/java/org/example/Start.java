package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repository.db.ChildDbRepository;
import org.example.repository.db.EventDbRepository;
import org.example.repository.db.LoginDbRepository;
import org.example.repository.db.SignupDbRepository;
import org.example.services.*;
import org.example.utils.AbstractServer;
import org.example.utils.ConcurrentServer;


import java.io.File;
import java.util.Properties;

public class Start {
    private static int defaultPort = 55555;
    private static Logger logger = LogManager.getLogger(Start.class);

    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try {
            serverProps.load(Start.class.getResourceAsStream("/server.properties"));
            logger.info("Server properties set {}", serverProps);
        } catch (Exception e) {
            logger.error("Error starting server: {}", e.getMessage());
            logger.debug("Looking into folder {}", new File(".").getAbsolutePath());
            return;
        }

        IService server = new Service(
                new ServiceChild(
                        new ChildDbRepository(serverProps)
                ),
                new ServiceEvent(
                        new EventDbRepository(serverProps)
                ),
                new ServiceSignup(
                        new SignupDbRepository(serverProps)
                ),
                new ServiceLogin(
                        new LoginDbRepository(serverProps)
                )
        );

        int serverPort = defaultPort;
        try{
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException e) {
            logger.error("Wrong port number {}", serverProps.getProperty("server.port"));
            logger.debug("Using default port {}", defaultPort);
        }
        logger.info("Starting server on port {}", serverPort);

        AbstractServer server1 = new ConcurrentServer(serverPort, server);
        try {
            server1.start();
        } catch (Exception e) {
            logger.error("Error starting the server {}", e.getMessage());
        } finally {
            try {
                server1.stop();
            } catch (Exception e) {
                logger.error("Error stopping server {}", e.getMessage());
            }
        }

    }
}
