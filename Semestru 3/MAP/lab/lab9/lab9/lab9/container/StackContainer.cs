using Task = lab9.model.Task;

namespace lab9.container
{
	internal class StackContainer : TaskContainer
	{
		public override Task? Remove()
		{
			if (!this.IsEmpty())
			{
				var toRemove = this.tasks.ElementAt(this.Size() - 1);
				this.tasks.RemoveAt(this.Size() - 1);
				return toRemove;
			}
			return null;
		}
	}
}
