package controller;

import domain.validators.ValidationException;
import service.ServiceUtilizator;
import domain.Utilizator;

import java.util.Scanner;

public class Ui {
    private ServiceUtilizator serviceUtilizator;

    public Ui(ServiceUtilizator serviceUtilizator_) {
        this.serviceUtilizator = serviceUtilizator_;
    }
    public void Run () {
        System.out.println("Welcome to SocialNetwork!\n");
        boolean exit = false;
        while (true) {
            System.out.print("" +
                    "A. User\n" +
                    "B. Friends\n" +
                    "C. Communities\n\n" +
                    "X. Exit\n\n" +
                    "Enter option: ");
            var read = new Scanner(System.in);
            var option = read.nextLine().trim().toUpperCase();
            switch (option) {
                case "A": {
                    System.out.print("" +
                            "A. Add user\n" +
                            "B. Remove User\n\n" +
                            "X. Exit\n\n" +
                            "Enter option: ");
                    var option2 = read.nextLine().trim().toUpperCase();
                    switch (option2) {
                        case "A": {
                            System.out.print("First name: ");
                            var firstName = read.nextLine().trim();
                            System.out.print("Last name: ");
                            var lastName = read.nextLine().trim();
                            System.out.print("ID: ");
                            Long id;
                            try {
                                id = Long.parseLong(read.nextLine().trim());
                            } catch (Exception e) {
                                System.out.println("Invalid ID");
                                break;
                            }
                            try {
                                this.serviceUtilizator.addUtilizator(
                                        firstName,
                                        lastName,
                                        id
                                );
                                System.out.println("User added\n");
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "B": {
                            System.out.print("Enter the ID of the user you want to remove: ");
                            Long id;
                            try {
                                id = Long.parseLong(read.nextLine().trim());
                            } catch (Exception e) {
                                System.out.println("Invalid ID");
                                break;
                            }
                            try {
                                this.serviceUtilizator.deleteUtilizator(id);
                                System.out.println("User removed\n");
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "X": {
                            break;
                        }
                    }
                    break;
                }
                case "B": {
                    System.out.print("" +
                            "A. Add friendship\n" +
                            "B. Remove friendship\n\n" +
                            "X. Exit\n\n" +
                            "Enter option: ");
                    var option2 = read.nextLine().trim().toUpperCase();
                    switch (option2) {
                        case "A": {

                        }
                        case "B": {

                        }
                        case "X": {
                            break;
                        }
                    }
                    break;
                }
                case "C": {
                    System.out.print("" +
                            "A. Count communities\n" +
                            "B. Larges community\n\n" +
                            "X. Exit\n\n" +
                            "Enter option: ");
                    var option2 = read.nextLine().trim().toUpperCase();
                    switch (option2) {
                        case "A": {

                        }
                        case "B": {

                        }
                        case "X": {
                            break;
                        }
                    }
                    break;
                }
                case "X": {
                    exit = true;
                    break;
                }
                default: {
                    System.out.println("Invalid option!\n");
                    break;
                }
            }
            if (exit) break;
        }
    }
}
