package org.example.protocol;

import org.example.domain.Entity;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private ResponseType Type;
    private Entity Entity;
    private List<Entity> Entities;
    private String Message;

    public ResponseType getType() {
        return Type;
    }

    public Response setType(ResponseType type) {
        Type = type;
        return this;
    }

    public Entity getEntity() {
        return Entity;
    }

    public Response setEntity(Entity entity) {
        Entity = entity;
        return this;
    }

    public List<Entity> getEntities() {
        return Entities;
    }

    public Response setEntities(List<Entity> entities) {
        Entities = entities;
        return this;
    }

    public String getMessage() {
        return Message;
    }

    public Response setMessage(String message) {
        Message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Response [Type= " + Type + ", Entity= " + Entity + ", Entities= " + Entities + ", Message= " + Message + "]";
    }
}
