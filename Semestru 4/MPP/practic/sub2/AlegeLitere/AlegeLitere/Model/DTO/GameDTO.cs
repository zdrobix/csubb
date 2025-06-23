using AlegeLitere.Model.Domain;

namespace AlegeLitere.Model.DTO
{
	public class GameDTO
	{
		public int Id { get; set; }
		public int GameConfigurationId { get; set; }
		public string Nickname { get; set; }
		public string Attempts { get; set; }
		public bool Won { get; set; }
		public int Points { get; set; }
		public DateTime StartedAt { get; set; }
	}
}
