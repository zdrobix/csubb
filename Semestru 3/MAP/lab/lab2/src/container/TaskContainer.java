package container;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskContainer implements Container {
    List<Task> tasks = new ArrayList<>();

    @Override
    public abstract Task remove();

    @Override
    public void add(Task task) {
        this.tasks.add(task);
    }

    @Override
    public int size() {
        return this.tasks.size();
    }

    @Override
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }
}
