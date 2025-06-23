using GasesteAnimalul.Model.DTO;
using GasesteAnimalul.Repo.Interface;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace GasesteAnimalul.Controllers
{
	[Route("api/[controller]")]
	[ApiController]
	public class GameController : ControllerBase
	{
		private IGameRepository GameRepository;

		public GameController(IGameRepository gameRepository)
		{
			GameRepository = gameRepository;
		}

		//https://localhost:xxxx/game
		[HttpPost]
		public async Task<IActionResult> AddGame([FromBody]AddGameRequest request)
		{
			var game = await this.GameRepository.AddGame(
				new Model.Domain.Game {
					Nickname = request.Nickname,
					GameConfigurationId = request.GameConfigurationId,
					Won = request.Won,
					Attempts = request.Attempts
				}
			);

			if (game == null) return NotFound();
			return Ok(new GameDTO
			{
				Id = game.Id,
				Nickname = game.Nickname,
				Attempts = game.Attempts,
				Won = game.Won,
				GameConfigurationId = request.GameConfigurationId,
			});
		}

		//https://localhost:xxxx/game
		[HttpGet]
		public async Task<IActionResult> GetGames()
		{
			var games = await this.GameRepository.GetAllGames();
			return Ok(
				games.Select(
					g => new GameDTO
					{
						Id = g.Id,
						GameConfigurationId = g.GameConfigurationId,
						Attempts = g.Attempts,
						Nickname = g.Nickname,
						Won = g.Won
					}	
				).ToList()
			);
		}

		//https://localhost:xxxx/game/won
		[HttpGet]
		[Route("won")]
		public async Task<IActionResult> GetWonGames()
		{
			var games = await this.GameRepository.GetWonGames();
			games = games.OrderBy(g => g.Attempts.Split(",").Count());
			return Ok(
				games.Select(
					g => new GameDTO
					{
						Id = g.Id,
						GameConfigurationId = g.GameConfigurationId,
						Attempts = g.Attempts,
						Nickname = g.Nickname,
						Won = g.Won
					}
				).ToList()
			);
		}

		//https://localhost:xxxx/game/lost
		[HttpGet]
		[Route("lost")]
		public async Task<IActionResult> GetLostGames()
		{
			var games = await this.GameRepository.GetLostGames();
			return Ok(
				games.Select(
					g => new GameDTO
					{
						Id = g.Id,
						GameConfigurationId = g.GameConfigurationId,
						Attempts = g.Attempts,
						Nickname = g.Nickname,
						Won = g.Won
					}
				).ToList()
			);
		}
	}
}
