namespace GasesteAnimalul.Model.Domain
{
	public class Game
	{
		public int Id { get; set; }
		public int GameConfigurationId { get; set; }
		public string Nickname { get; set; }
		public string Attempts { get; set; }
		public bool Won { get; set; }
	}
}
