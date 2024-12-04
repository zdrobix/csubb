using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Task = lab9.model.Task;


namespace lab9.container
{
	internal interface IContainerr
	{
		abstract Task? Remove();
		void Add(Task task);
		int Size();
		bool IsEmpty();
	}
}
