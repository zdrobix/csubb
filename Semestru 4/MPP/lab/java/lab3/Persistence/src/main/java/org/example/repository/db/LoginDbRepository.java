package org.example.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Entity;
import org.example.domain.LoginInfo;
import org.example.repository.interf.ILoginRepository;
import org.example.repository.utils.CommonUtils;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class LoginDbRepository implements ILoginRepository {

    private CommonUtils Utils;
    private static final Logger logger = LogManager.getLogger();

    public LoginDbRepository(Properties properties) {
        logger.info("Initializing LoginDbRepository with properties: {}", properties);
        this.Utils = new CommonUtils(properties);
    }

    @Override
    public Entity FindById(Object o) {
        return null;
    }

    @Override
    public Iterable FindAll() {
        return null;
    }

    @Override
    public Entity Save(Entity entity) {
        return null;
    }

    @Override
    public Entity Update(Entity entity) {
        return null;
    }

    @Override
    public Entity Delete(Object o) {
        return null;
    }

    @Override
    public LoginInfo GetByUsername (String username) {
        logger.traceEntry("Getting login info for username " + username);
        Connection connection = this.Utils.GetConnection();
        LoginInfo loginInfo = null;
        try (var statement = connection.prepareStatement("select * from login where username = ?"))
        {
            statement.setString(1, username);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    loginInfo = (LoginInfo) new LoginInfo(resultSet.getString(2), resultSet.getString(3))
                            .SetId(resultSet.getInt(1));
                } else return null;
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return loginInfo;
    }

}
