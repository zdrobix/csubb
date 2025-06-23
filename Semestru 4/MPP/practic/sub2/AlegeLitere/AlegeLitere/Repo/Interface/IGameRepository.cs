using AlegeLitere.Model.Domain;

namespace AlegeLitere.Repo.Interface
{
	public interface IGameRepository
	{
		Task<Game> AddGame(Game game);
		Task<IEnumerable<Game>> GetAllGames();
		Task<IEnumerable<Game>?> GetWonGames();
		Task<IEnumerable<Game>?> GetLostGames();
		Task<IEnumerable<Game>?> GetSimilarGames();
	}
}
