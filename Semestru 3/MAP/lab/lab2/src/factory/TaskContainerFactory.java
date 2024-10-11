package sem1_2.factory;

import sem1_2.model.Container;
import sem1_2.model.StackContainer;

public class TaskContainerFactory implements Factory {
    @Override
    public Container createContainer(Strategy strategy) {
        if (strategy == Strategy.FIFO) {
            // return new QueueContainer();
            return null;
        } else {
            return new StackContainer();
        }
    }
}
