using System.Net.Http;

namespace TestClient
{
	public class Application
	{
		private static string ApiBaseUrl = "https://localhost:7140";
		private static readonly HttpClient client = new HttpClient();

		static async Task GetChildren(string baseUrl)
		{
			HttpResponseMessage response = await client.GetAsync(baseUrl + "/api/child");
			string result = await response.Content.ReadAsStringAsync();
			Console.WriteLine("\nGET all children response:\n\n");
			Console.WriteLine(result + "\n\n------------------------\n\n");
		}

		static async Task DeleteChildren(string baseUrl, int id)
		{
			HttpResponseMessage response = await client.DeleteAsync($"{baseUrl}/api/child/{id}");
			string result = await response.Content.ReadAsStringAsync();
			Console.WriteLine($"\nDELETE child with id= {id}:\n\n");
			Console.WriteLine(result + "\n\n------------------------\n\n");

		}
		public static async Task Main(string[] args)
		{
			await GetChildren(ApiBaseUrl);
			await DeleteChildren(ApiBaseUrl, 1004);
			await GetChildren(ApiBaseUrl);
		}
	}
}