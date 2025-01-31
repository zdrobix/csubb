package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Integer ID;
    private int TableID;
    private List<Integer> ItemsIDs;
    private LocalDateTime Date;
    private OrderStatus Status;

    public Order(Integer ID, int TableID, List<Integer> ItemsIDs, LocalDateTime Date, OrderStatus Status) {
        this.ID = ID;
        this.TableID = TableID;
        this.ItemsIDs = ItemsIDs;
        this.Date = Date;
        this.Status = Status;
    }

    public Integer getID() {
        return ID;
    }

    public int getTableID() {
        return TableID;
    }

    public List<Integer> getItemsIDs() {
        return ItemsIDs;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public OrderStatus getStatus() {
        return Status;
    }
}
