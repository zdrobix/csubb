using GasesteAnimalul.Model.Domain;

namespace GasesteAnimalul.Model.DTO
{
	public class AddGameRequest
	{
		public int GameConfigurationId { get; set; }
		public string Nickname { get; set; }
		public string Attempts { get; set; }
		public bool Won { get; set; }
	}
}
