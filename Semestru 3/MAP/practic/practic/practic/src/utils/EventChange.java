package utils;


public class EventChange implements Event {
    private Object data;
    private String type;

    public EventChange (Object data, String type) {
        this.data = data;
        this.type = type;  
    }

    public Object getData() {
        return data;
    }


    public String getType() {
        return type;
    }
}
