using GasesteAnimalul.Model.Domain;

namespace GasesteAnimalul.Repo.Interface
{
	public interface IGameConfigRepository
	{
		Task<GameConfig> GetRandomGameConfig();
		Task<GameConfig?> GetGameConfig(int id);

		Task<GameConfig> AddGameConfig(GameConfig gameConfig);
	}
}
