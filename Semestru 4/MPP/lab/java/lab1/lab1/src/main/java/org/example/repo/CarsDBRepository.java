package org.example.repo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.JdbcUtils;
import org.example.domain.Car;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository {

    private JdbcUtils dbUtils;


    private static final Logger logger = LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry("Getting all the cars from " + manufacturerN);
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try (var statement = con.prepareStatement("select * from cars where manufacturer = ?")) {
            statement.setString(1, manufacturerN);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        //to do
        return null;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("Saving car " + elem);
        Connection con = dbUtils.getConnection();
        try (var statement = con.prepareStatement("insert into cars(manufacturer, model, year) values (?, ?, ?)"))
        {
            statement.setString(1, elem.getManufacturer());
            statement.setString(2, elem.getModel());
            statement.setLong(3, elem.getYear());
            int result = statement.executeUpdate();
        } catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
}

    @Override
    public void update(Integer integer, Car elem) {
        logger.traceEntry("Updating car " + elem);
        Connection con = dbUtils.getConnection();
        try (var statement = con.prepareStatement("update cars set manufacturer = ?, model = ?, year = ? where id = ?"))
        {
            statement.setString(1, elem.getManufacturer());
            statement.setString(2, elem.getModel());
            statement.setLong(3, elem.getYear());
            statement.setInt(4, integer);
            int result = statement.executeUpdate();
        } catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry("Getting all the cars");
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try (var statement = con.prepareStatement("select * from cars")) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error" + e);
        }
        logger.traceExit();
        return cars;
    }
}
