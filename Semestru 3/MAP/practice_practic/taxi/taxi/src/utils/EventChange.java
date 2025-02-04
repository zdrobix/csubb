package utils;

import domain.Order;

public class EventChange implements IEvent{
    private Order Data;        
    private Order OldData;     
    private String Type;
    
    public EventChange(String type, Order data, Order oldData) {
        this.Type = type;
        this.Data = data;
        this.OldData = oldData;
    }

    public Order getData() {
        return Data;
    }

    public Order getOldData() {
        return OldData;
    }

    public String getType() {
        return Type;
    }
}
