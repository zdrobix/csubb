using AlegeLitere.Data;
using AlegeLitere.Model.Domain;
using AlegeLitere.Repo.Interface;

using Microsoft.EntityFrameworkCore;

namespace AlegeLitere.Repo.Implementation
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

		public async Task<GameConfig> UpdateGameConfig (GameConfig gameConfig)
		{
			var existing = await dbContext.GameConfigurations.FirstOrDefaultAsync(g => g.Id == gameConfig.Id);
			if (existing == null) return null;
			dbContext.Entry(existing).CurrentValues.SetValues(gameConfig);
			await dbContext.SaveChangesAsync();
			return existing;
		}

		public async Task<IEnumerable<GameConfig>> GetAll() =>
			await dbContext.GameConfigurations.ToListAsync();
	}
}
