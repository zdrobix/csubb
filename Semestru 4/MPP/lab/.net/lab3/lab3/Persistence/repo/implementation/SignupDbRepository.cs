using Model.app.domain;
using Persistence.app.repo.@interface;
using Persistence.app.utils;
using log4net;
using System.Data.SQLite;

namespace Persistence.app.repo.implementation
{
	public class SignupDbRepository : ISignupRepository
	{
        private CommonUtils Utils;
		private static readonly ILog _logger = LogManager.GetLogger(typeof(EventDbRepository));

		public SignupDbRepository()
		{
			_logger.Info("Initialized SignupDbRepository.");
			this.Utils = new CommonUtils();
		}

		public Signup Create(Signup e)
		{
			_logger.Info($"Saving the signup: {e.Child.Name}, {e.Event.Name}");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				using (var statement = new SQLiteCommand("insert into signups (id_child, id_event) values (@id_child, @id_event)", connection))
				{
					statement.Parameters.Clear();
					statement.Parameters.AddWithValue("@id_child", e.Child.Id);
					statement.Parameters.AddWithValue("@id_event", e.Event.Id);
					int rows = statement.ExecuteNonQuery();
					_logger.Info($"Affected rows: {rows}");
					return (Signup) e.SetId(Tuple.Create(e.Child.Id, e.Event.Id));
				}
			}
			catch (Exception ex)
			{
				_logger.Info($"Caught exception: " + ex);
				Console.WriteLine(ex.Message);
			}
			return null!;
		}

		public Signup? Delete(Tuple<int, int> id)
		{
			throw new NotImplementedException();
		}

		public IEnumerable<Signup> GetAll()
		{
			_logger.Info($"Searching for all signups ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Signup> signups = new List<Signup>();
				using (var query = new SQLiteCommand("select * from signups", connection))
				{
					using (var reader = query.ExecuteReader())
					{
						while (reader.Read())
							signups.Add(
								(Signup)new Signup(
									
								).SetId(Tuple.Create(
									reader.GetInt32(reader.GetOrdinal("id_child")),
									reader.GetInt32(reader.GetOrdinal("id_event"))
								))
						   );
						return signups;
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

		public IEnumerable<Signup> GetAllByChildId(int id)
		{
			_logger.Info($"Searching for all signups ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Signup> signups = new List<Signup>();
				using (var query = new SQLiteCommand(
						"SELECT c.id AS child_id, c.name AS child_name, c.cnp AS child_cnp, " +
						"e.id AS event_id, e.name AS event_name, e.min_age, e.max_age " +
						"FROM signups s " +
						"JOIN children c ON s.id_child = c.id " +
						"JOIN events e ON s.id_event = e.id " +
						"WHERE c.id = @id_child", connection))
				{
					query.Parameters.AddWithValue("@id_child", id);
					using (var reader = query.ExecuteReader())
					{
						while (reader.Read())
						{
							var child = new Child
							{
								Id = reader.GetInt32(reader.GetOrdinal("child_id")),
								Name = reader.GetString(reader.GetOrdinal("child_name")),
								Cnp = reader.GetString(reader.GetOrdinal("child_cnp"))
							};

							var event_ = new Event
							{
								Id = reader.GetInt32(reader.GetOrdinal("event_id")),
								Name = reader.GetString(reader.GetOrdinal("event_name")),
								MinAge = reader.GetInt32(reader.GetOrdinal("min_age")),
								MaxAge = reader.GetInt32(reader.GetOrdinal("max_age"))
							};
							signups.Add((Signup)new Signup(child, event_).SetId(Tuple.Create(child.Id, event_.Id)));
						}
						return signups;
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

		public IEnumerable<Signup> GetAllByEventId(int id)
		{
			_logger.Info($"Searching for all signups ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Signup> signups = new List<Signup>();
				using (var query = new SQLiteCommand(
						"SELECT c.id AS child_id, c.name AS child_name, c.cnp AS child_cnp, " +
						"e.id AS event_id, e.name AS event_name, e.min_age, e.max_age " +
						"FROM signups s " +
						"JOIN children c ON s.id_child = c.id " +
						"JOIN events e ON s.id_event = e.id " +
						"WHERE e.id = @id_event", connection))
				{
					query.Parameters.AddWithValue("@id_event", id);
					using (var reader = query.ExecuteReader())
					{
						while (reader.Read())
						{
							var child = new Child
							{
								Id = reader.GetInt32(reader.GetOrdinal("child_id")),
								Name = reader.GetString(reader.GetOrdinal("child_name")),
								Cnp = reader.GetString(reader.GetOrdinal("child_cnp"))
							};

							var event_ = new Event
							{
								Id = reader.GetInt32(reader.GetOrdinal("event_id")),
								Name = reader.GetString(reader.GetOrdinal("event_name")),
								MinAge = reader.GetInt32(reader.GetOrdinal("min_age")),
								MaxAge = reader.GetInt32(reader.GetOrdinal("max_age"))
							};
							signups.Add((Signup)new Signup(child, event_).SetId(Tuple.Create(child.Id, event_.Id)));
						}
						return signups;
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

		public Signup? GetById(Tuple<int, int> id)
		{
			_logger.Info($"Searching for all signups ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Signup> signups = new List<Signup>();
				using (var query = new SQLiteCommand("select * from signups where id_child = @id_child and id_event = @id_event", connection))
				{
					query.Parameters.AddWithValue("@id_child", id.Item1);
					query.Parameters.AddWithValue("@id_event", id.Item2);
					using (var reader = query.ExecuteReader())
					{
						if (reader.Read())
							return (Signup)new Signup(

								).SetId(Tuple.Create(
									reader.GetInt32(reader.GetOrdinal("id_child")),
									reader.GetInt32(reader.GetOrdinal("id_event"))
								)
						   );
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

		public Signup? Update(Signup e)
		{
			throw new NotImplementedException();
		}

		public IEnumerable<Signup> GetAllJoin()
		{
			_logger.Info($"Searching for all signups joined ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Signup> result = new List<Signup>();
				using (var query = new SQLiteCommand(
					"SELECT c.id AS child_id, c.name AS child_name, c.cnp AS child_cnp, " +
					"e.id AS event_id, e.name AS event_name, e.min_age, e.max_age " +
					"FROM signups s " +
					"JOIN children c ON s.id_child = c.id " +
					"JOIN events e ON s.id_event = e.id", connection))
				{
					using (var reader = query.ExecuteReader())
					{
						while (reader.Read())
						{
							var child = new Child
							{
								Id = reader.GetInt32(reader.GetOrdinal("child_id")),
								Name = reader.GetString(reader.GetOrdinal("child_name")),
								Cnp = reader.GetString(reader.GetOrdinal("child_cnp"))
							};

							var event_ = new Event
							{
								Id = reader.GetInt32(reader.GetOrdinal("event_id")),
								Name = reader.GetString(reader.GetOrdinal("event_name")),
								MinAge = reader.GetInt32(reader.GetOrdinal("min_age")),
								MaxAge = reader.GetInt32(reader.GetOrdinal("max_age"))
							};
							result.Add((Signup)new Signup(child, event_).SetId(Tuple.Create(child.Id, event_.Id)));
						}
						return result;
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

	}
}
