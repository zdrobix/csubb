using GasesteAnimalul.Model.Domain;

namespace GasesteAnimalul.Model.DTO
{
	public class GameDTO
	{
		public int Id { get; set; }
		public int GameConfigurationId { get; set; }
		public string Nickname { get; set; }
		public string Attempts { get; set; }
		public bool Won { get; set; }
	}
}
