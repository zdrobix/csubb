package repo;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.Order;
import domain.OrderStatus;

public class RepoOrders {
    private List<Order> Orders;
    private Integer NextOrderID;

    public RepoOrders(){
        Orders = new ArrayList<>();
        this.NextOrderID = 1;
        this.LoadFromFile();
    }

    private void LoadFromFile() {

        try {
            var fileWriter = new Scanner(new File("Q:\\info\\csubb\\Semestru 3\\MAP\\practice_practic\\restaurant\\restaurant\\src\\input\\orders.txt"));
            while (fileWriter.hasNextLine()) {
                var line = fileWriter.nextLine();
                var parts = line.split(",");
                this.Orders.add(
                    new Order(
                        Integer.parseInt(parts[0]), 
                        Integer.parseInt(parts[1]), 
                        RepoItems.OrderItemsForID(Integer.parseInt(parts[0])), 
                        LocalDateTime.parse(parts[2]), 
                        OrderStatus.valueOf(parts[3]))
                );
                if (Integer.parseInt(parts[0]) == this.NextOrderID) {
                    this.NextOrderID ++;
                }
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddOrder (Order order){
        Orders.add(order);
        this.NextOrderID ++;
        try {
            var fileWriter = new FileWriter("Q:\\info\\csubb\\Semestru 3\\MAP\\practice_practic\\restaurant\\restaurant\\src\\input\\orders.txt", true);
            fileWriter.write(order.getID() + "," + order.getTableID() + "," + order.getDate() + "," + order.getStatus() + "\n");

            var fileWriterItems = new FileWriter("Q:\\info\\csubb\\Semestru 3\\MAP\\practice_practic\\restaurant\\restaurant\\src\\input\\order_items.txt", true);
            for (var itemID : order.getItemsIDs()) {
                fileWriterItems.write(order.getID() + ":" + itemID + "\n");
            }
            fileWriter.close();
            fileWriterItems.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer GetNextOrderID() {
        return this.NextOrderID;
    }

    public List<Order> GetOrders() {
        return this.Orders;
    }
}
