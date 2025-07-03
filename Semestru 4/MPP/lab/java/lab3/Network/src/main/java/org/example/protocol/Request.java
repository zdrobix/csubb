package org.example.protocol;

import org.example.domain.Entity;
import org.example.services.IObserver;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {
    private RequestType Type;
    private Entity Entity;
    private List<Entity> Entities;
    private String Message;

    public RequestType getType() {
        return Type;
    }

    public Request setType(RequestType type) {
        Type = type;
        return this;
    }

    public Entity getEntity() {
        return Entity;
    }

    public Request setEntity(Entity entity) {
        Entity = entity;
        return this;
    }

    public List<Entity> getEntities() {
        return Entities;
    }

    public Request setEntities(List<Entity> entities) {
        Entities = entities;
        return this;
    }

    public String getMessage() {
        return Message;
    }

    public Request setMessage(String message) {
        Message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Request [Type= " + Type + ", Entity= " + Entity + ", Entities= " + Entities + ", Message= " + Message + "]";
    }
}
