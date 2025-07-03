using Model.app.domain;
using Persistence.app.repo.@interface;
using Persistence.app.utils;
using log4net;
using System.Data.SQLite;

namespace Persistence.app.repo.implementation
{
	public class EventDbRepository : IEventRepository
	{
		private CommonUtils Utils;
		private static readonly ILog _logger = LogManager.GetLogger(typeof(EventDbRepository));

		public EventDbRepository()
		{
			_logger.Info("Initialized EventDbRepository.");
			this.Utils = new CommonUtils();
		}

		public Event Create(Event e)
		{
			_logger.Info($"Saving the event: {e.Name}, {e.MinAge} - {e.MaxAge}");
			try
			{
				_logger.Info("Succesfully connected to the db.");
				var connection = this.Utils.GetConnection();
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				using (var statement = new SQLiteCommand("insert into events (name, min_age, max_age) values (@name, @min_age, @max_age)", connection))
				{
					statement.Parameters.Clear();
					statement.Parameters.AddWithValue("@name", e.Name);
					statement.Parameters.AddWithValue("@min_age", e.MinAge);
					statement.Parameters.AddWithValue("@max_age", e.MaxAge);
					int rows = statement.ExecuteNonQuery();
					_logger.Info($"Affected rows: {rows}");
					return (Event)new Event(e.Name, e.MinAge, e.MaxAge).SetId((int)connection.LastInsertRowId);
				}
			}
			catch (Exception ex)
			{
				_logger.Info($"Caught exception: " + ex);
				Console.WriteLine(ex.Message);
			}
			return null!;
		}

		public Event? Delete(int id)
		{
			throw new NotImplementedException();
		}

		public IEnumerable<Event> GetAll()
		{
			_logger.Info($"Searching for all events ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Event> events = new List<Event>();
				using (var query = new SQLiteCommand("select * from events",connection))
				{
					using (var reader = query.ExecuteReader())
					{
						while (reader.Read())
							events.Add(
								(Event)new Event(
									reader.GetString(reader.GetOrdinal("name")),
									reader.GetInt32(reader.GetOrdinal("min_age")),
									reader.GetInt32(reader.GetOrdinal("max_age"))
								).SetId(reader.GetInt32(reader.GetOrdinal("id")))
						   );
						return events;
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

		public IEnumerable<Event> GetAllByMaxAge(int maxAge)
		{
			_logger.Info($"Searching for all events ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Event> events = new List<Event>();
				using (var query = new SQLiteCommand("select * from events where max_age = @max_age", connection))
				{
					query.Parameters.Clear();
					query.Parameters.AddWithValue("@max_age", maxAge);
					using (var reader = query.ExecuteReader())
					{
						while (reader.Read())
							events.Add(
								(Event)new Event(
									reader.GetString(reader.GetOrdinal("name")),
									reader.GetInt32(reader.GetOrdinal("min_age")),
									reader.GetInt32(reader.GetOrdinal("max_age"))
								).SetId(reader.GetInt32(reader.GetOrdinal("id")))
						   );
						return events;
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

		public IEnumerable<Event> GetAllByMinAge(int minAge)
		{
			_logger.Info($"Searching for all events ");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				List<Event> events = new List<Event>();
				using (var query = new SQLiteCommand("select * from events where min_age = @min_age", connection))
				{
					query.Parameters.Clear();
					query.Parameters.AddWithValue("@min_age", minAge);
					using (var reader = query.ExecuteReader())
					{
						while (reader.Read())
							events.Add(
								(Event)new Event(
									reader.GetString(reader.GetOrdinal("name")),
									reader.GetInt32(reader.GetOrdinal("min_age")),
									reader.GetInt32(reader.GetOrdinal("max_age"))
								).SetId(reader.GetInt32(reader.GetOrdinal("id")))
						   );
						return events;
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

		public Event? GetById(int id)
		{
			_logger.Info($"Searching for the event with id: {id}");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				using (var query = new SQLiteCommand("select * from events where id = @id", connection))
				{
					query.Parameters.Clear();
					query.Parameters.AddWithValue("@id", id);
					using (var reader = query.ExecuteReader())
					{
						if (reader.Read())
							return (Event)new Event(
									reader.GetString(reader.GetOrdinal("name")),
									reader.GetInt32(reader.GetOrdinal("min_age")),
									reader.GetInt32(reader.GetOrdinal("max_age"))
								).SetId(reader.GetInt32(reader.GetOrdinal("id")));
						return null;
					}
				}
			}
			catch (Exception ex)
			{
				_logger.Info($"Caught exception: " + ex);
				Console.WriteLine(ex.Message);
			}
			return null;
		}

		public Event? Update(Event e)
		{
			_logger.Info($"Updating the event: {e.Name}, , {e.MinAge} - {e.MaxAge}");
			var connection = this.Utils.GetConnection();
			try
			{
				_logger.Info("Succesfully connected to the db.");
				if (connection.State != System.Data.ConnectionState.Open)
					connection.Open();
				var oldEvent = this.GetById(e.Id);
				if (oldEvent == null)
				{
					_logger.Info($"Didn't find any event with id = {e.Id}");
					return null!;
				}
				using (var statement = new SQLiteCommand("update events set name = @name, min_age = @min_age, max_age = @max_age where id = @id", connection))
				{
					statement.Parameters.Clear();
					statement.Parameters.AddWithValue("@name", e.Name);
					statement.Parameters.AddWithValue("@min_age", e.MinAge);
					statement.Parameters.AddWithValue("@max_age", e.MaxAge);
					statement.Parameters.AddWithValue("@id", e.Id);
					int rows = statement.ExecuteNonQuery();
					_logger.Info($"Affected rows: {rows}");
					return oldEvent;
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
