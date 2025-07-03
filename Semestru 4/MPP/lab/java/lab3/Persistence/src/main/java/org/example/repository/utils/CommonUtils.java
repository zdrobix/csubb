package org.example.repository.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class CommonUtils {
    private Properties JdbcProperties;

    private static final Logger logger = LogManager.getLogger();

    public CommonUtils(Properties properties) {
        this.JdbcProperties = properties;
    }

    private Connection Instance = null;

    private Connection GetNewConnection() {
        logger.traceEntry();
        String url = this.JdbcProperties.getProperty("jdbc.url");
        String user = this.JdbcProperties.getProperty("jdbc.user");
        String password = this.JdbcProperties.getProperty("jdbc.pass");
        logger.info("trying to connect to database ... {}",url);
        logger.info("user: {}",user);
        logger.info("pass: {}", password);
        Connection connection=null;
        try {
            if (user != null && password != null)
                connection = DriverManager.getConnection(url, user, password);
            else connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error trying to get connnection " + e);
        }
        return connection;
    }

    public Connection GetConnection() {
        logger.traceEntry();
        try {
            if (this.Instance == null || this.Instance.isClosed())
                this.Instance = GetNewConnection();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error trying to get connnection " + e);
        }
        logger.traceExit(this.Instance);
        return this.Instance;
    }
}
