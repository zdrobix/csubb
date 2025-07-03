using Model.app.domain;
using Persistence.app.repo.@interface;
using Persistence.app.utils;
using log4net;
using System.Data.SQLite;

namespace Persistence.app.repo.implementation
{
	public class ChildDbRepository : IChildRepository
	{
		private CommonUtils Utils;
		private static readonly ILog _logger = LogManager.GetLogger(typeof(ChildDbRepository));

		public ChildDbRepository ()
		{
			_logger.Info("Initialized ChildDbRepository.");
			this.Utils = new CommonUtils();
		}

		public Child Create(Child e)
		{
			_logger.Info($"Saving the child: {e.Name}, {e.Cnp}");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				using (var statement = new SQLiteCommand("insert into children (name, cnp) values (@name, @cnp)", connection))
				{
					statement.Parameters.Clear();
					statement.Parameters.AddWithValue("@name", e.Name);
					statement.Parameters.AddWithValue("@cnp", e.Cnp);
					int rows = statement.ExecuteNonQuery();
					_logger.Info($"Affected rows: {rows}");
					return (Child) new Child(e.Name, e.Cnp).SetId((int)connection.LastInsertRowId);
				}
			}
			catch (Exception ex)
			{
				_logger.Info($"Caught exception: " + ex);
                Console.WriteLine(ex.Message);
			}
			return null!;
		}

		public Child? Delete(int id)
		{
			throw new NotImplementedException();
		}

		public IEnumerable<Child> GetAll()
		{
			_logger.Info($"Searching for all children ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Child> children = new List<Child>();
				using (var query = new SQLiteCommand("select * from children", connection))
				{
					using (var reader = query.ExecuteReader())
					{
						while (reader.Read())
							children.Add(
								(Child)new Child(
									reader.GetString(reader.GetOrdinal("name")),
									reader.GetString(reader.GetOrdinal("cnp"))
								).SetId(reader.GetInt32(reader.GetOrdinal("id")))
						   );
						return children;
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

		public IEnumerable<Child> GetAllByAge(int age)
		{
			throw new NotImplementedException();
		}

		public Child? GetById(int id)
		{
			_logger.Info($"Searching for the child with id: {id}");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				using (var query = new SQLiteCommand("select * from children where id = @id", connection))
				{
					query.Parameters.Clear();
					query.Parameters.AddWithValue("@id", id);
					using (var reader = query.ExecuteReader())
					{
						if (reader.Read())
							return (Child)new Child(
									reader.GetString(reader.GetOrdinal("name")),
									reader.GetString(reader.GetOrdinal("cnp"))
								).SetId(reader.GetInt32(reader.GetOrdinal("id")));
						return null;
					}
				}
			} catch (Exception ex)
			{
				_logger.Info($"Caught exception: " + ex);
				Console.WriteLine(ex.Message);
			}
			return null;
		}

		public Child? Update(Child e)
		{
			_logger.Info($"Updating the child: {e.Name}, {e.Cnp}");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				var oldChild = this.GetById(e.Id);
				if (oldChild == null)
				{
					_logger.Info($"Didn't find any child with id = {e.Id}");
						return null!;
				}
				using (var statement = new SQLiteCommand("update children set name = @name, cnp = @cnp where id = @id", connection))
				{
					statement.Parameters.Clear();
					statement.Parameters.AddWithValue("@name", e.Name);
					statement.Parameters.AddWithValue("@cnp", e.Cnp);
					statement.Parameters.AddWithValue("@id", e.Id);
					int rows = statement.ExecuteNonQuery();
					_logger.Info($"Affected rows: {rows}");
					return oldChild;
				}
			}
			catch (Exception ex)
			{
				_logger.Info($"Caught exception: " + ex);
				Console.WriteLine(ex.Message);
			}
			return null;
		}
	}
}
