using Task = lab9.model.Task;

namespace lab9.decorator
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
