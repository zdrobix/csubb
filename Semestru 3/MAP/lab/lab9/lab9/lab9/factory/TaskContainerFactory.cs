using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using lab9.container;

namespace lab9.factory
{
	class TaskContainerFactory : IFactory
	{
		private static TaskContainerFactory instance = new TaskContainerFactory();
		private TaskContainerFactory() { }

		public static TaskContainerFactory getInstance() => instance!;

		public IContainerr createContainer(Strategy strategy)
		{
			switch (strategy)
			{
				case Strategy.FIFO:
					{
						return new QueueContainer();
					}
				case Strategy.LIFO:
					{
						return new StackContainer();
					}
				default: throw new Exception("Strategy not recognised " + strategy);
			}
		}
	}
}
