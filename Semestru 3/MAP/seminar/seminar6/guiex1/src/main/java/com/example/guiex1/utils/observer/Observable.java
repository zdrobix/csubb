package main.java.com.example.guiex1.utils.observer;


import main.java.com.example.guiex1.utils.events.IEvent;

public interface Observable<E extends IEvent> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}
