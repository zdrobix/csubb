using System;
using Task = lab9.model.Task;

namespace lab9.container
{
	internal abstract class TaskContainer : IContainerr
	{
		public List<Task> tasks = new List<Task>();
		public void Add(Task task) => tasks.Add(task);
		public bool IsEmpty() => tasks.Count == 0;
		public abstract Task? Remove();
		public int Size() => tasks.Count;
	}
}
