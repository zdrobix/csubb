using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace lab9.container
{
	internal class QueueContainer : TaskContainer
	{
		public Task? Remove ()
		{
			if (!this.IsEmpty())
				return this.tasks.remove(0);
			return null;
		}
	}
}
