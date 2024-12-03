using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace lab9.model
{
	internal class PrinterTaskRunner : AbstractTaskRunner
	{

		public PrinterTaskRunner(ITaskRunner taskRunner) : base(taskRunner)
		{
		}

		public void ExecuteOneTask()
		{
			this.ExecuteOneTask();
            Console.WriteLine("Task executed at ");
		}
	}
}
