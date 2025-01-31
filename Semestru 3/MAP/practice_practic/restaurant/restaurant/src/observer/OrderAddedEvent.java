package observer;

import domain.Order;

public class OrderAddedEvent implements IEvent {
    private Order Data;    
    public OrderAddedEvent(Order Data) {
        this.Data = Data;
    }

    public Order getData() {
        return Data;
    }
}
