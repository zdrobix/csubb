using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace lab9.model
{
	internal interface ITaskRunner
	{
		void ExecuteOneTask();
		void ExecuteAll();
		void AddTask(Task t);
		bool HasTask();
	}
}
