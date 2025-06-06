﻿using Task = lab9.model.Task;

namespace lab9.container
{
	internal class QueueContainer : TaskContainer
	{
		public override Task? Remove()
		{
			if (!this.IsEmpty())
			{
				var toRemove = this.tasks.ElementAt(0);
				this.tasks.RemoveAt(0);
				return toRemove;
			}
			return null;
		}
	}
}
