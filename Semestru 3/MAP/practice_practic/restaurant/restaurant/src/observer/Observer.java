package observer;

public interface Observer<E extends IEvent> {
    void update(E e);
}