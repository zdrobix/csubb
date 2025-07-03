package org.example.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Child;
import org.example.repository.interf.IChildRepository;
import org.example.repository.utils.CommonUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ChildDbRepository implements IChildRepository {

    private CommonUtils Utils;
    private static final Logger logger = LogManager.getLogger();

    public ChildDbRepository(Properties properties) {
        logger.info("Initializing ChildDbRepository with properties: {}", properties);
        this.Utils = new CommonUtils(properties);
    }

    @Override
    public Iterable<Child> GetAllByAge(int age) {
        logger.traceEntry("Getting all of the children of age " + age);
        Connection connection = this.Utils.GetConnection();
        List<Child> children = new ArrayList<>();
        int year = LocalDateTime.now().getYear() % 100 - age;
        String yearString;
        if (year < 10)
            yearString = "_0" + year + "%";
        else yearString = "_" + year + "%";
        try (var statement = connection.prepareStatement("select * from children where cnp like ? ")) {
            statement.setString(1, yearString);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next())
                    children.add(
                            (Child)new Child(
                                    resultSet.getString("name"),
                                    resultSet.getString("cnp")
                            ).SetId(resultSet.getInt("id"))
                    );
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return children;
    }

    @Override
    public Child FindById(Integer integer) {
        logger.traceEntry("Looking for child with id: {}", integer);
        Connection connection = this.Utils.GetConnection();
        Child child = null;
        try (var statement = connection.prepareStatement("select * from children where id = ?"))
        {
            statement.setInt(1, integer);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    child = (Child) new Child(resultSet.getString(2), resultSet.getString(3))
                            .SetId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return child;
    }

    @Override
    public Iterable<Child> FindAll() {
        logger.traceEntry("Getting all of the children.");
        Connection connection = this.Utils.GetConnection();
        List<Child> children = new ArrayList<>();
        try (var statement = connection.prepareStatement("select * from children")) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next())
                    children.add(
                            (Child)new Child(
                                    resultSet.getString("name"),
                                    resultSet.getString("cnp")
                            ).SetId(resultSet.getInt("id"))
                    );

            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return children;
    }

    @Override
    public Child Save(Child entity) {
        logger.traceEntry("Saving child " + entity);
        Connection connection = this.Utils.GetConnection();
        try (var statement = connection.prepareStatement("insert into children (name, cnp) values (?, ?)"))
        {
            statement.setString(1, entity.GetName());
            statement.setString(2, entity.GetCNP());
            int result = statement.executeUpdate();
            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return (Child) entity.SetId(generatedId);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error " + e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Child Update(Child entity) {
        logger.traceEntry("Updating child " + entity);
        Connection connection = this.Utils.GetConnection();
        Child oldChild = this.FindById(entity.GetId());
        try (var statement = connection.prepareStatement("update children set name = ?, cnp = ? where id = ?"))
        {
            statement.setString(1, entity.GetName());
            statement.setString(2, entity.GetCNP());
            statement.setInt(3, entity.GetId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error " + e);
        }
        logger.traceExit();
        return oldChild;
    }

    @Override
    public Child Delete(Integer integer) {
        return null;
    }
}
