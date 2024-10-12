package factory;

import container.Container;

public interface Factory {
    Container createContainer(Strategy strategy);
}

