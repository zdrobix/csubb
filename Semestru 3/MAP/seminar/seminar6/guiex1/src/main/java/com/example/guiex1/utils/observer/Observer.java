package main.java.com.example.guiex1.utils.observer;


import main.java.com.example.guiex1.utils.events.IEvent;

public interface Observer<E extends IEvent> {
    void update(E e);
}