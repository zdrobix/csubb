package container;

import model.Task;

public interface Container {
    abstract Task remove();
    void add(Task task);
    int size();
    boolean isEmpty();
}
