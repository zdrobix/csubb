using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using lab9.container;
using lab9.factory;
using Task = lab9.model.Task;

namespace lab9.decorator
{
	internal class StrategyTaskRunner : ITaskRunner
	{
		private IContainerr container;

		public StrategyTaskRunner(Strategy strategy)
		{
			container = TaskContainerFactory.getInstance().createContainer(strategy);
		}

		public void AddTask(Task t) => this.container.Add(t);

		public void ExecuteAll()
		{
			while (this.HasTask())
				this.ExecuteOneTask();
		}

		public void ExecuteOneTask() => this.container.Remove().execute();
		public bool HasTask() => this.container.Size() > 0;
	}
}
