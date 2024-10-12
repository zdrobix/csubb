package factory;

import container.Container;
import container.StackContainer;
import container.QueueContainer;

public class TaskContainerFactory implements Factory {
    private static TaskContainerFactory instance = new TaskContainerFactory();

    private TaskContainerFactory() {}

    public static TaskContainerFactory getInstance() {
        return instance;
    }

    @Override
    public Container createContainer(Strategy strategy) {
        switch (strategy) {
            case Strategy.FIFO: {
                return new QueueContainer();
            }
            case Strategy.LIFO: {
                return new StackContainer();
            }
            default: throw new IllegalArgumentException("Strategy not recognised " + strategy);
        }
    }
}
