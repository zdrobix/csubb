package controller;

import domain.validators.ValidationException;
import service.Service;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Ui {
    private Service serviceUtilizator;

    public Ui(Service serviceUtilizator_) {
        this.serviceUtilizator = serviceUtilizator_;
    }
    public void Run () {
        System.out.println("Welcome to SocialNetwork!\n");
        //this.serviceUtilizator.printAll();
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
                            Long id1 = 0L, id2 = 0L;
                            try {
                                System.out.print("Enter the ID of the first friend: ");
                                id1 = Long.parseLong(read.nextLine().trim());
                                System.out.print("Enter the ID of the second friend: ");
                                id2 = Long.parseLong(read.nextLine().trim());
                            } catch (Exception e) {
                                System.out.println("Invalid ID");
                            }
                            try {
                                this.serviceUtilizator.addPrietenie(id1, id2);
                                System.out.println("Friendship added\n");
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "B": {
                            Long id1 = 0L, id2 = 0L;
                            try {
                                System.out.print("Enter the ID of the first friend: ");
                                id1 = Long.parseLong(read.nextLine().trim());
                                System.out.print("Enter the ID of the second friend: ");
                                id2 = Long.parseLong(read.nextLine().trim());
                            } catch (Exception e) {
                                System.out.println("Invalid ID");
                            }
                            try {
                                this.serviceUtilizator.deletePrietenie(id1, id2);
                                System.out.println("Friendship deleted\n");
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
                case "C": {
                    System.out.print("" +
                            "A. Count communities\n" +
                            "B. Larges community\n\n" +
                            "X. Exit\n\n" +
                            "Enter option: ");
                    var option2 = read.nextLine().trim().toUpperCase();
                    switch (option2) {
                        case "A": {
                            var number = this.serviceUtilizator.numberOfCommunities();
                            if (number == 1)
                                System.out.println("There is a single community.");
                            else System.out.println("There are " + number + " communities");
                            break;
                        }
                        case "B": {
                            break;
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
