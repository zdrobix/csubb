package container;

import model.Task;

public class QueueContainer extends TaskContainer {

    @Override
    public Task remove() {
        if (!this.isEmpty())
            return super.tasks.remove(0);
        return null;
    }
}
