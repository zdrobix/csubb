using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using lab9.container;

namespace lab9.factory
{
	internal interface IFactory
	{
		IContainerr createContainer(Strategy strategy);
	}
}
