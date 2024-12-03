using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab9.model
{
	internal class DelayTaskRunner : AbstractTaskRunner
	{
		private static int miliseconds = 3000;

		public DelayTaskRunner( ITaskRunner runner)
			: base(runner)
		{
			
		}

		public void ExecuteOneTask()
		{
			Thread.Sleep(miliseconds);
			this.ExecuteOneTask();
		}
	}
}
