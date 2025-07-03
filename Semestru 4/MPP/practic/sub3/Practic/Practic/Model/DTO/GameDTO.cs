using Practic.Model.Domain;

namespace Practic.Model.DTO
{
	public class GameDTO
	{
		public int Id { get; set; }
		public string GameConfigAnimals { get; set; }
		public string Nickname { get; set; }
		public int Points { get; set; }
		public int Guessed { get; set; }
		public int Duration { get; set; }
	}
}
