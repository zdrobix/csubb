package container;

import model.Task;

public class StackContainer extends TaskContainer {

    @Override
    public Task remove() {
        if (!this.isEmpty())
            return super.tasks.remove(tasks.size() - 1);
        return null;
    }
}
