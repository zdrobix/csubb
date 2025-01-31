package repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;


public class RepoItems {
    public static List<Integer> OrderItemsForID(int ID) {
        var order_items = new ArrayList<Integer>();
        try {
            var fileWriter = new Scanner(new File("Q:\\info\\csubb\\Semestru 3\\MAP\\practice_practic\\restaurant\\restaurant\\src\\input\\order_items.txt"));
            while (fileWriter.hasNextLine()) {
                var line = fileWriter.nextLine();
                var parts = line.split(":");
                if (Integer.parseInt(parts[0]) == ID) {
                    order_items.add(Integer.parseInt(parts[1]));
                }
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }   
        return order_items;
    }
}