using lab10.domain;
using Npgsql;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.repo
{
	internal class Repository<E, ID> : IRepository<E, ID> where E : Entity<ID>
	{
		private string TableName;
		public Repository() => this.TableName = TypeMatching<E, ID>.GetTableName();
		private NpgsqlConnection getConnection() => new NpgsqlConnection("Host=localhost;Port=5432;Username=postgres;Password=password;Database=nbaromania;");
		
		public void Delete(ID id)
		{
			if (id == null)
				throw new ArgumentNullException(nameof(id), "Id cannot be null.");
			if (this.Find(id) == null)
				throw new ArgumentException(id + " entity not found.");

			using (var connection = this.getConnection())
			{
				connection.Open();
				string query = $"DELETE FROM " + this.TableName + $" WHERE " + TypeMatching<E, ID>.ReturnCondition(id) + ";";
				using (var command = new NpgsqlCommand(query, connection))
				{
					if (command.ExecuteNonQuery() <= 0)
						throw new Exception("The entity was found but couldn't be deleted.");
				}
			}
		}

		public E? Find(ID id)
		{
			if (id == null)
				throw new ArgumentNullException("Id cannot be null.");
			using (var connection = this.getConnection())
			{
				connection.Open();
				string query = "";
				query = $"SELECT * FROM " + this.TableName + $" WHERE " + TypeMatching<E, ID>.ReturnCondition(id) + $";";

				using (var command = new NpgsqlCommand(query, connection))
				{
					using (var reader = command.ExecuteReader())
					{
						if (reader.Read())
						{
							List<string> resultlist = new List<string>();
							for (int i = 0; i < reader.FieldCount; i++)
								resultlist.Add(reader[i].ToString()!);
							return TypeMatching<E, ID>.CreateEntityFromList(resultlist);
						}
					}
				}
			}
			return null;
		}

		public IEnumerable<E> FindAll()
		{
			List<E> resultlist = new List<E>();
			using (var connection = this.getConnection())
			{
				connection.Open();
				string query = $"SELECT * FROM {this.TableName};";
				using (var command = new NpgsqlCommand(query, connection))
				{
					var result = command.ExecuteReader();
					while (result.Read())
					{
						List<string> entityList = new List<string>();
						for (int i = 0; i < result.FieldCount; i++)
							entityList.Add(result[i].ToString()!);
						resultlist.Add(TypeMatching<E, ID>.CreateEntityFromList(entityList)!);
					}
				}
			}
			return resultlist;
		}

		public E? Save(E Entity)
		{
			if (Entity == null)
				throw new ArgumentNullException("Entity cannot be null.");
			if (this.Find(Entity.getId()) != null)
				throw new ArgumentNullException("Entity with id " + Entity.getId()!.ToString() + " already exists.");

			int rowsAffected = 0;
			using (var connection = this.getConnection())
			{
				connection.Open();
				string query = $"INSERT INTO " + this.TableName + $" VALUES " + TypeMatching<E, ID>.CreateListFromEntity(Entity) + ";";
				using (var command = new NpgsqlCommand(query, connection))
				{
					rowsAffected = command.ExecuteNonQuery();
				}
			}
			if (rowsAffected > 0)
				return Entity;
			return null;
		}

		public E? Update(E Entity)
		{
			throw new NotImplementedException();
		}
	}
}
