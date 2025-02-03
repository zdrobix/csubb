package backend.utils;

import backend.domain.Animal;

public class EventChange implements Event {
    private Animal data;
    private Animal oldData;
    private String type;

    public EventChange (Animal data, Animal old, String type) {
        this.data = data;
        this.oldData = old;
        this.type = type;  
    }

    public Animal getData() {
        return data;
    }

    public Animal getOldData() {
        return oldData;
    }

    public String getType() {
        return type;
    }
}
