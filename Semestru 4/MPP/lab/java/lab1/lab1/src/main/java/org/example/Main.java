package org.example;

import org.example.domain.Car;
import org.example.repo.CarRepository;
import org.example.repo.CarsDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        try {
            CarRepository carRepo = new CarsDBRepository(props);
            carRepo.add(new Car("Tesla", "Model S", 2019));
            System.out.println("Toate masinile din db");
            for (Car car : carRepo.findAll())
                System.out.println(car);
            String manufacturer = "Tesla";
            System.out.println("Masinile produse de " + manufacturer);
            for (Car car : carRepo.findByManufacturer(manufacturer))
                System.out.println(car);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}