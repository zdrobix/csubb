using Networking.protocol;
using Services.services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Networking.utils
{
	public class Server : AbstractConcurrentServer
	{
		private IService Server_;

		public Server(string host, int port, IService server) : base(host, port)
		{
			this.Server_ = server;
		}

		protected override Thread createWorker(TcpClient client)
		{
			Worker worker = new Worker(Server_, client);
			return new Thread(new ThreadStart(worker.run));
		}
	}
}
