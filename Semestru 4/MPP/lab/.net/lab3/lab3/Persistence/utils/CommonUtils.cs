using log4net;
using System.Configuration;
using System.Data.SQLite;

namespace Persistence.app.utils
{
	internal class CommonUtils
	{
		private static readonly ILog _logger = LogManager.GetLogger(typeof(CommonUtils));
		private SQLiteConnection Instance = null;
		public CommonUtils() { }

		private SQLiteConnection GetNewConnection ()
		{
			_logger.Info("Getting new connection.");
			this.Instance = new SQLiteConnection(ConfigurationManager.ConnectionStrings["children_events"].ConnectionString);
			return this.Instance;
		}

		public SQLiteConnection GetConnection()
		{
			_logger.Info("Getting ordinary connection.");
			if (this.Instance == null || this.Instance.State != System.Data.ConnectionState.Open)
				this.Instance = this.GetNewConnection();
			_logger.Info("Connection granted.");
			return this.Instance;
		}
	}
}
