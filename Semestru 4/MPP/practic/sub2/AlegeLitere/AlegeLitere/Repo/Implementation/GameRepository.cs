using AlegeLitere.Data;
using AlegeLitere.Model.Domain;
using AlegeLitere.Repo.Interface;
using Microsoft.EntityFrameworkCore;

namespace AlegeLitere.Repo.Implementation
{
	public class GameRepository : IGameRepository
	{
		private readonly ApplicationDbContext dbContext;

		public GameRepository(ApplicationDbContext dbContext)
		{
			this.dbContext = dbContext;
		}

		public async Task<Game> AddGame(Game game)
		{
			await dbContext.Games.AddAsync(game);
			await dbContext.SaveChangesAsync();
			return game;
		}

		public async Task<IEnumerable<Game>> GetAllGames() =>
			await dbContext.Games
				.ToListAsync();

		public async Task<IEnumerable<Game>?> GetWonGames() =>
			await dbContext.Games
				.Where(g => g.Won)
				.ToListAsync();

		public async Task<IEnumerable<Game>?> GetLostGames() =>
			await dbContext.Games
				.Where(g => !g.Won)
				.ToListAsync();

		public async Task<IEnumerable<Game>?> GetSimilarGames()
		{
			var games = await dbContext.Games.ToListAsync();

			return games.Where(g =>
			{
				if (string.IsNullOrEmpty(g.Attempts))
					return false;

				string[] attempts = g.Attempts.Split(',');
				for (var i = 0; i < attempts.Length - 1; i++)
				{
					if (attempts[i] == attempts[i + 1])
						return true;
				}
				return false;
			});
		}
	}
}
