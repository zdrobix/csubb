using GasesteAnimalul.Data;
using GasesteAnimalul.Model.Domain;
using GasesteAnimalul.Repo.Interface;

using Microsoft.EntityFrameworkCore;

namespace GasesteAnimalul.Repo.Implementation
{
	public class GameConfigRepository : IGameConfigRepository
	{
		private readonly ApplicationDbContext dbContext;

		public GameConfigRepository(ApplicationDbContext dbContext)
		{
			this.dbContext = dbContext;
		}

		public async Task<GameConfig> GetRandomGameConfig()
		{
			var ids = dbContext.GameConfigurations.Select(g => g.Id);
			var randomIndex = new Random().Next(0, ids.Count());
			var randomId = ids.ElementAt(randomIndex);

			return await dbContext.GameConfigurations.FirstOrDefaultAsync(g => g.Id == randomId!)!;
		}

		public async Task<GameConfig?> GetGameConfig(int id) =>
			await dbContext.GameConfigurations.FirstOrDefaultAsync(g => g.Id == id);

		public async Task<GameConfig> AddGameConfig (GameConfig gameConfig)
		{
			await dbContext.AddAsync(gameConfig);
			await dbContext.SaveChangesAsync();

			return gameConfig;
		}
	}
}
