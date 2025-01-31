package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.MenuItem;
import domain.Order;
import domain.OrderStatus;
import domain.Table;
import repo.RepoMenu;
import repo.RepoOrders;
import repo.RepoTables;
import observer.*;

public class Service implements Observable<OrderAddedEvent> {
    private RepoOrders Repo;
    private RepoMenu Menu;
    private RepoTables Tables;

    private List<Observer<OrderAddedEvent>> observers=new ArrayList<>();

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
        this.notifyObservers(new OrderAddedEvent(order));
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

    @Override
    public void addObserver(Observer<OrderAddedEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<OrderAddedEvent> e) {
    }

    @Override
    public void notifyObservers(OrderAddedEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}
