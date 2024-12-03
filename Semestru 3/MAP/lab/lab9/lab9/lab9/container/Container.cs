using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab9.container
{
	internal interface IContainer
	{
		abstract Task remove();
		void add(Task task);
		int size();

		bool isEmpty();
	}
}
