using Model.app.domain;
using Persistence.app.repo.implementation;
using Persistence.app.utils;
using log4net;
using System.Data.SQLite;
using Persistence.app.repo.@interface;

namespace Persistence.app.repo.implementation
{
	public class LoginInfoDbRepository : ILoginRepository
	{
		private CommonUtils Utils;
		private static readonly ILog _logger = LogManager.GetLogger(typeof(ChildDbRepository));

		public LoginInfoDbRepository()
		{
			_logger.Info("Initialized LoginInfoDbRepository.");
			this.Utils = new CommonUtils();
		}

		public LoginInfo? Delete(int id)
		{
			throw new NotImplementedException();
		}

		public IEnumerable<LoginInfo> GetAll()
		{
			throw new NotImplementedException();
		}

		public LoginInfo? GetById(int id)
		{
			throw new NotImplementedException();
		}

		public LoginInfo GetByUsername(string username)
		{
			_logger.Info($"Searching for the login with username: {username}");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				using (var query = new SQLiteCommand("select * from login where username = @username", connection))
				{
					query.Parameters.Clear();
					query.Parameters.AddWithValue("@username", username);
					using (var reader = query.ExecuteReader())
					{
						if (reader.Read())
							return (LoginInfo)new LoginInfo(
									username,
									reader.GetString(reader.GetOrdinal("password"))
								).SetId(reader.GetInt32(reader.GetOrdinal("id")));
					}
				}
			}
			catch (Exception ex)
			{
				_logger.Info($"Caught exception: " + ex);
				Console.WriteLine(ex.Message);
			}
			return null!;
		}

		public LoginInfo? Update(LoginInfo e)
		{
			throw new NotImplementedException();
		}

		LoginInfo IRepository<int, LoginInfo>.Create(LoginInfo e)
		{
			throw new NotImplementedException();
		}
	}
}
