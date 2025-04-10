using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2
{
	internal class AppConfig
	{
		public static IConfigurationRoot Configuration { get; }

		static AppConfig()
		{
			Configuration = new ConfigurationBuilder()
				.SetBasePath(Directory.GetCurrentDirectory()) 
				.AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
				.Build();
		}

		public static string GetConnectionString(string name = "DefaultConnection") =>
			Configuration.GetSection("ConnectionStrings")[name]!;

		public static string GetParentTableName(string config = "Config1") =>
			Configuration.GetSection(config)["ParentTable"]!;

		public static string GetChildTableName(string config = "Config1") =>
			Configuration.GetSection(config)["ChildTable"]!;

		public static string GetChildTableColumnNames(string config = "Config1") =>
			Configuration.GetSection(config)["ChildTableColumnNames"]!;

		public static string GetParentTableColumnNames(string config = "Config1") =>
			Configuration.GetSection(config)["ParentTableColumnNames"]!;
	}
}
