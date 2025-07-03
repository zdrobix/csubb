package org.example.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Event;
import org.example.repository.interf.IEventRepository;
import org.example.repository.utils.CommonUtils;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EventDbRepository implements IEventRepository {
    private CommonUtils Utils;
    private static final Logger logger = LogManager.getLogger();

    public EventDbRepository(Properties properties) {
        logger.info("Initializing ChildDbRepository with properties: {}", properties);
        this.Utils = new CommonUtils(properties);
    }

    @Override
    public Iterable<Event> GetAllByMinAge(int minAge) {
        logger.traceEntry("Getting all of the where min age = " + minAge);
        Connection connection = this.Utils.GetConnection();
        List<Event> events = new ArrayList<>();
        try (var statement = connection.prepareStatement("select * from events where min_age = ?")) {
            statement.setInt(1, minAge);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next())
                    events.add(
                            (Event)new Event(
                                    resultSet.getString("name"),
                                    resultSet.getInt("min_age"),
                                    resultSet.getInt("max_age")
                            ).SetId(resultSet.getInt("id"))
                    );

            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return events;
    }

    @Override
    public Iterable<Event> GetAllByMaxAge(int maxAge) {
        logger.traceEntry("Getting all of the where max age = " + maxAge);
        Connection connection = this.Utils.GetConnection();
        List<Event> events = new ArrayList<>();
        try (var statement = connection.prepareStatement("select * from events where min_age = ?")) {
            statement.setInt(1, maxAge);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next())
                    events.add(
                            (Event)new Event(
                                    resultSet.getString("name"),
                                    resultSet.getInt("min_age"),
                                    resultSet.getInt("max_age")
                            ).SetId(resultSet.getInt("id"))
                    );

            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return events;
    }

    @Override
    public Event FindById(Integer integer) {
        logger.traceEntry("Looking for event with id: {}", integer);
        Connection connection = this.Utils.GetConnection();
        Event event = null;
        try (var statement = connection.prepareStatement("select * from events where id = ?"))
        {
            statement.setInt(1, integer);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    event = (Event) new Event(resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4))
                            .SetId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return event;
    }

    @Override
    public Iterable<Event> FindAll() {
        logger.traceEntry("Getting all of the events.");
        Connection connection = this.Utils.GetConnection();
        List<Event> events = new ArrayList<>();
        try (var statement = connection.prepareStatement("select * from events")) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next())
                    events.add(
                            (Event)new Event(
                                    resultSet.getString("name"),
                                    resultSet.getInt("min_age"),
                                    resultSet.getInt("max_age")
                            ).SetId(resultSet.getInt("id"))
                    );

            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return events;
    }

    @Override
    public Event Save(Event entity) {
        logger.traceEntry("Saving event " + entity);
        Connection connection = this.Utils.GetConnection();
        try (var statement = connection.prepareStatement("insert into events (name, min_age, max_age) values (?, ?, ?)"))
        {
            statement.setString(1, entity.GetName());
            statement.setInt(2, entity.GetMinAge());
            statement.setInt(3, entity.GetMaxAge());
            int result = statement.executeUpdate();
            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return (Event) entity.SetId(generatedId);
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
    public Event Update(Event entity) {
        logger.traceEntry("Updating event " + entity);
        Connection connection = this.Utils.GetConnection();
        Event oldEvent = this.FindById(entity.GetId());
        try (var statement = connection.prepareStatement("update events set name = ?, min_age = ?, max_age = ? where id = ?"))
        {
            statement.setString(1, entity.GetName());
            statement.setInt(2, entity.GetMinAge());
            statement.setInt(3, entity.GetMaxAge());
            statement.setInt(4, entity.GetId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error " + e);
        }
        logger.traceExit();
        return oldEvent;
    }

    @Override
    public Event Delete(Integer integer) {
        return null;
    }
}
