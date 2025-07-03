using Client.app.controler;
using log4net;
using log4net.Config;
using Networking.protocol;
using System.Configuration;
using System.Diagnostics;
using System.Reflection;

namespace Client
{
	internal static class Start
	{
		private static readonly ILog Log = LogManager.GetLogger(typeof(Start));

		[STAThread]
		static void Main()
		{
			var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly()!);
			XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config")); ; 
			LogManager.GetLogger(typeof(Start)).Info("\n\n********************Application Started******************\n");

			int Port = ConfigurationManager.AppSettings["Port"] != null ? int.Parse(ConfigurationManager.AppSettings["Port"]!) : 0;
			string Ip = ConfigurationManager.AppSettings["Ip"]!;
			Log.Info($"Using server on port {Port} and ip {Ip}.");

			Controller controller = new Controller(new Proxy(Ip, Port));

			ApplicationConfiguration.Initialize();
			Application.Run(new Window(controller));
		}
	}
}