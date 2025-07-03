using log4net;
using log4net.Config;
using Microsoft.EntityFrameworkCore;
using Protos;
using System.Configuration;
using System.Reflection;
using Microsoft.Extensions.DependencyInjection;
using Services.services;
using Server.app.service;
using Persistence.app.repo.implementation;
//using Persistence.data;
//using Persistence.repo_async;
//using Persistence.repo_async.implementation;
//using Services.services_async;
//using Server.app.service_async;


namespace Server
{
	public class Start
	{
		private static readonly ILog Log = LogManager.GetLogger(typeof(Start));

		public static async Task Main(string[] args)
		{
			Log.Info("First step.");
			var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly()!);
			XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));

			Log.Info("Starting server...");

			IService service = new Service(
					new ServiceChild(new ChildDbRepository()),
					new ServiceEvent(new EventDbRepository()),
					new ServiceSignup(new SignupDbRepository()),
					new ServiceLogin(new LoginInfoDbRepository())
			);
			//var services = new ServiceCollection();
			//services.AddDbContext<AppDbContext>(options =>
			//	options.UseSqlite(ConfigurationManager.ConnectionStrings["data_source"].ConnectionString));

			//services.AddScoped<IChildRepository, ChildRepository>();
			//services.AddScoped<IEventRepository, EventRepository>();
			//services.AddScoped<ISignupRepository, SignupRepository>();
			//services.AddScoped<ILoginInfoRepository, LoginInfoRepository>();

			//services.AddScoped<IServiceChild, ServiceChild>();
			//services.AddScoped<IServiceEvent, ServiceEvent>();
			//services.AddScoped<IServiceSignup, ServiceSignup>();
			//services.AddScoped<IServiceLogin, ServiceLogin>();
			//services.AddScoped<IService, Service>();

			//var serviceProvider = services.BuildServiceProvider();
			//var service = serviceProvider.GetService<IService>();

			int Port = ConfigurationManager.AppSettings["Port"] != null ? int.Parse(ConfigurationManager.AppSettings["Port"]!) : 0;
			string Ip = ConfigurationManager.AppSettings["Ip"]!;
			Log.Info($"Server started on port {Port} and ip {Ip}.");

			//Networking.utils.Server server = new Networking.utils.Server(Ip, Port, service);
			var server = new GRPCServer(Ip, Port, service);
			try { await server.Start(new CancellationToken()); }
			catch (Exception e)
			{
				Log.Error("Error starting server: " + e.Message);
				Console.WriteLine("Error starting server: " + e.Message);
				return;
			}
			Log.Info("Server started.");
			Console.WriteLine("Press ENTER to exit...");
			Console.ReadLine();
		}
	}
}
