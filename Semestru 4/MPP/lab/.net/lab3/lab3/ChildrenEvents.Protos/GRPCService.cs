using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Services.services;

namespace ChildrenEvents.Protos
{
	internal class GRPCService
	{
		private readonly string Host;
		private readonly string Port;
		private readonly IService Service;
	}
}
