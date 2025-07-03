using Practic.Model.Domain;

namespace Practic.Repo.Interface
{
	public interface IGameConfigRepository
	{
		Task<GameConfig> GetRandomGameConfig();
		Task<GameConfig?> GetGameConfig(int id);
		Task<GameConfig> AddGameConfig(GameConfig gameConfig);
		Task<GameConfig> UpdateGameConfig(GameConfig gameConfig);
		Task<IEnumerable<GameConfig>> GetAll();
	}
}
