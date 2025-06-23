using GasesteAnimalul.Model.Domain;

namespace GasesteAnimalul.Repo.Interface
{
	public interface IGameRepository
	{
		Task<Game> AddGame(Game game);
		Task<IEnumerable<Game>> GetAllGames();
		Task<IEnumerable<Game>?> GetWonGames();
		Task<IEnumerable<Game>?> GetLostGames();
	}
}
