package controller;

import domain.validators.ValidationException;
import service.Service;

import java.sql.SQLException;
import java.util.Scanner;

public class Ui {
    private final Service serviceUtilizator;

    public Ui(Service serviceUtilizator_) {
        this.serviceUtilizator = serviceUtilizator_;
    }
    public void Run ()  {
        System.out.println("Welcome to SocialNetwork!\n");
        boolean exit = false;
        do {
            System.out.print(
                    """
                            A. User
                            B. Friends
                            C. Communities
                            
                            X. Exit
                            
                            Enter option:\s""");
            var read = new Scanner(System.in);
            var option = read.nextLine().trim().toUpperCase();
            switch (option) {
                case "A": {
                    System.out.print(
                            """
                                    A. Add user
                                    B. Remove User
                                    
                                    X. Exit
                                    
                                    Enter option:\s""");
                    var option2 = read.nextLine().trim().toUpperCase();
                    switch (option2) {
                        case "A": {
                            System.out.print("First name: ");
                            var firstName = read.nextLine().trim();
                            System.out.print("Last name: ");
                            var lastName = read.nextLine().trim();
                            System.out.print("ID: ");
                            long id;
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
                            long id;
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
                            } catch (SQLException e) {
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
                    System.out.print(
                            """
                                    A. Add friendship
                                    B. Remove friendship
                                    
                                    X. Exit
                                    
                                    Enter option:\s""");
                    var option2 = read.nextLine().trim().toUpperCase();
                    switch (option2) {
                        case "A": {
                            long id1 = 0L, id2 = 0L;
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
                            long id1 = 0L, id2 = 0L;
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
                    System.out.print(
                            """
                                    A. Count communities
                                    B. Larges community
                                    
                                    X. Exit
                                    
                                    Enter option:\s""");
                    var option2 = read.nextLine().trim().toUpperCase();
                    switch (option2) {
                        case "A": {
                            Integer number;
                            try {
                                number = this.serviceUtilizator.numberOfCommunities();
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            if (number == 1)
                                System.out.println("There is a single community.");
                            else System.out.println("There are " + number + " communities");
                            break;
                        }
                        case "B": {
                            try {
                                System.out.println("The largest community is: ");
                                for (var userId : this.serviceUtilizator.largestCommunity()) {
                                    System.out.print(this.serviceUtilizator.getUtilizator(userId).getFirstName() + " ");
                                }
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                            System.out.println();
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
        } while (!exit);
    }
}
