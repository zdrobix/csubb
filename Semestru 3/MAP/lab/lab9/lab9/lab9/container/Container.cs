using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace lab9.container
{
	internal interface IContainer
	{
		abstract Task Remove();
		void Add(Task task);
		int Size();

		bool IsEmpty();
	}
}
