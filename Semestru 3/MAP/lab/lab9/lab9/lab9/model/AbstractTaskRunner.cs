using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab9.model
{
	internal abstract class AbstractTaskRunner : ITaskRunner
	{
		private ITaskRunner taskRunner;

		public AbstractTaskRunner(ITaskRunner taskRunner) {
			this.taskRunner = taskRunner;
		}

		public void AddTask(Task t)
		{
			taskRunner.AddTask(t);
		}

		public void ExecuteAll()
		{
			while (this.HasTask())
			{
				taskRunner.ExecuteOneTask();
			}
		}

		public void ExecuteOneTask()
		{
			taskRunner.ExecuteOneTask();
		}

		public bool HasTask()
		{
			return taskRunner.HasTask();
		}
	}
}
