using Microsoft.Data.SqlClient;
using System;

namespace deadlock
{
	public class Application 
	{
		private static string ConnectionString = "Server=DESKTOP-9PS0RE2;Database=zdrobix;Trusted_Connection=True;TrustServerCertificate=True;";
		private const int MaxRetryCount = 3;

		public static void Main(string[] args)
		{
			var task1 = Task.Run(() => Transaction1());
			var task2 = Task.Run(() => Transaction2());

			Task.WaitAll(task1, task2);
			Console.WriteLine("Done.");
		}

		static void Transaction1()
		{
			int attempt = 0;
			while (attempt <  MaxRetryCount) 
			{
				try
				{
					using (var connection = new SqlConnection(ConnectionString))
					{
						connection.Open();

						var transaction = connection.BeginTransaction();

						var command1 = new SqlCommand(
							"UPDATE CLIENTI SET nume='Deadlock Client 2' " +
							"WHERE nume= 'Deadlock Client 1'", connection, transaction
						);
						command1.ExecuteNonQuery();
						Console.WriteLine("Transaction1: Updated CLIENTI.");
						Thread.Sleep(5000);

						var command2 = new SqlCommand(
							"UPDATE BENEFICII SET nume='Deadlock Beneficiu 2' " +
							"WHERE nume= 'Deadlock Beneficiu 1'", connection, transaction
						);
						command2.ExecuteNonQuery();

						transaction.Commit();
						Console.WriteLine("Transaction1: Committed.");
						break;
					}
				}
				catch (SqlException ex) when (ex.Number == 1205)
				{
					attempt++;
					Console.WriteLine($"Transaction1: Deadlock. Retry {attempt}/{MaxRetryCount}");
					Thread.Sleep(500);
				}
				catch (Exception ex)
				{
					Console.WriteLine("Transaction 1 failed: " + ex.Message);
				}
			}
			if (attempt == MaxRetryCount)
				Console.WriteLine("Aborted.");
		}

		static void Transaction2()
		{
			int attempt = 0;
			while (attempt < MaxRetryCount)
			{
				try
				{
					using (var connection = new SqlConnection(ConnectionString))
					{
						connection.Open();

						var transaction = connection.BeginTransaction();

						var command1 = new SqlCommand(
							"UPDATE BENEFICII SET nume='Deadlock Beneficiu 3' " +
							"WHERE nume= 'Deadlock Beneficiu 1'", connection, transaction
						);
						command1.ExecuteNonQuery();
						Console.WriteLine("Transaction2: Updated BENEFICII.");
						Thread.Sleep(5000);

						var command2 = new SqlCommand(
							"UPDATE CLIENTI SET nume='Deadlock Client 3' " +
							"WHERE nume= 'Deadlock Client 1'", connection, transaction
						);
						command2.ExecuteNonQuery();

						transaction.Commit();
						Console.WriteLine("Transaction2: Committed.");
						break;
					}
				}
				catch (SqlException ex) when (ex.Number == 1205)
				{
					attempt++;
					Console.WriteLine($"Transaction2: Deadlock. Retry {attempt}/{MaxRetryCount}");
					Thread.Sleep(500);
				}
				catch (Exception ex)
				{
					Console.WriteLine("Transaction 2 failed: " + ex.Message);
				}
			}
			if (attempt == MaxRetryCount)
				Console.WriteLine("Aborted.");
		}
	}
}