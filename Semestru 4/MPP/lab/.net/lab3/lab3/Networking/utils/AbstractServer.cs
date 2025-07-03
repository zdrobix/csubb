using log4net;
using System.Net.Sockets;
using System.Net;

namespace Networking.utils
{
	public abstract class AbstractServer
	{
		private TcpListener Server;
		private String Host;
		private int Port;

		private static readonly ILog Log = LogManager.GetLogger(typeof(AbstractServer));
		public AbstractServer(String host, int port)
		{
			this.Host = host;
			this.Port = port;
		}
		public void Start()
		{
			IPAddress adr = IPAddress.Parse(Host);
			IPEndPoint ep = new IPEndPoint(adr, Port);
			Server = new TcpListener(ep);
			Server.Start();
			while (true)
			{
				Log.Debug("Waiting for clients ...");
				TcpClient client = Server.AcceptTcpClient();
				Log.Debug("Client connected ...");
				processRequest(client);
			}
		}

		public void Stop()
		{
			if (Server != null)
				Server.Stop();
		}

		public abstract void processRequest(TcpClient client);

	}
}
