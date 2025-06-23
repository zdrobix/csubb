using GasesteAnimalul.Data;
using GasesteAnimalul.Model.Domain;
using GasesteAnimalul.Repo.Interface;
using Microsoft.EntityFrameworkCore;

namespace GasesteAnimalul.Repo.Implementation
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
	}
}
