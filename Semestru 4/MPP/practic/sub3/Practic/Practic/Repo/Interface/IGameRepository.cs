using Practic.Model.Domain;

namespace Practic.Repo.Interface
{
	public interface IGameRepository
	{
		Task<Game> AddGame(Game game);
		Task<IEnumerable<Game>> GetAllGames();
		Task<IEnumerable<Game>?> GetWonGames();
		Task<IEnumerable<Game>?> GetGamesPlus2Guessed();
	}
}
