using Practic.Model.Domain;

namespace Practic.Model.DTO
{
	public class AddGameRequest
	{
		public string GameConfigAnimals { get; set; }
		public string Nickname { get; set; }
		public int Points { get; set; }
		public int Guessed{ get; set; }
		public int Duration{ get; set; }
	}
}
