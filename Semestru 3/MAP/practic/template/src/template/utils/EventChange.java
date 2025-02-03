package template.utils;

public class EventChange implements IEvent {
    private Object Data;        //boxing
    private Object OldData;     
    private String Type;
    
    public EventChange(String type, Object data, Object oldData) {
        this.Type = type;
        this.Data = data;
        this.OldData = oldData;
    }

    public Object getData() {
        return Data;
    }

    public Object getOldData() {
        return OldData;
    }

    public String getType() {
        return Type;
    }
}
