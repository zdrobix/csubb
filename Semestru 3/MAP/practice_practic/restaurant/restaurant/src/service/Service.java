package service;

import java.time.LocalDateTime;
import java.util.List;

import domain.MenuItem;
import domain.Order;
import domain.OrderStatus;
import domain.Table;
import repo.RepoMenu;
import repo.RepoOrders;
import repo.RepoTables;

public class Service {
    private RepoOrders Repo;
    private RepoMenu Menu;
    private RepoTables Tables;

    public Service (RepoOrders repo, RepoMenu menu, RepoTables tables){
        this.Repo = repo;
        this.Menu = menu;
        this.Tables = tables;
    }

    public void AddOrder (int tableID, List<Integer> itemsIDs){
        for (var id : itemsIDs) 
            if (this.Menu.GetMenu().stream().noneMatch(x -> x.getID() == id))
                throw new IllegalArgumentException("Item with ID " + id + " not found in the menu");
        if (itemsIDs.isEmpty())
            throw new IllegalArgumentException("Items list is empty");
        var order = new Order(
            this.Repo.GetNextOrderID(),
            tableID,
            itemsIDs,
            LocalDateTime.now(),
            OrderStatus.Placed
        );
        this.Repo.AddOrder(order);
    }

    public List<MenuItem> GetMenu() {
        return this.Menu.GetMenu();
    }

    public List<Order> GetOrders() {
        return this.Repo.GetOrders();
    }

    public List<Table> GetTables() {
        return this.Tables.GetTables();
    }
}
