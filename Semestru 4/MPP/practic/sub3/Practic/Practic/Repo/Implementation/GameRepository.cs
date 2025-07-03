using Practic.Data;
using Practic.Model.Domain;
using Practic.Repo.Interface;
using Microsoft.EntityFrameworkCore;
using Serilog;

namespace Practic.Repo.Implementation
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
			Log.Information($"User {game.Nickname} added a game.\n");
			await dbContext.Games.AddAsync(game);
			await dbContext.SaveChangesAsync();
			return game;
		}

		public async Task<IEnumerable<Game>> GetAllGames() =>
			await dbContext.Games
				.ToListAsync();

		public async Task<IEnumerable<Game>?> GetWonGames() =>
			await dbContext.Games
				.Where(g => g.Points > 2)
				.ToListAsync();

		public async Task<IEnumerable<Game>?> GetGamesPlus2Guessed() =>
			await dbContext.Games
				.Where(g => g.Guessed > 1)
				.ToListAsync();
	}
}
