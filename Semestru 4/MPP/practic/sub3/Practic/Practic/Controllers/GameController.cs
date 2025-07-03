using Practic.Model.DTO;
using Practic.Repo.Interface;
using Azure.Core;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace Practic.Controllers
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
					GameConfigAnimals = request.GameConfigAnimals,
					Points = request.Points,
					Guessed = request.Guessed,
					Duration = request.Duration
				}
			);

			if (game == null) return NotFound();
			return Ok(new GameDTO
			{
				Id = game.Id,
				Nickname = game.Nickname,
				GameConfigAnimals = request.GameConfigAnimals,
				Points = request.Points,
				Guessed = request.Guessed,
				Duration = request.Duration
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
						GameConfigAnimals = g.GameConfigAnimals,
						Nickname = g.Nickname,
						Points = g.Points,
						Guessed = g.Guessed,
						Duration = g.Duration
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
			games = games.OrderByDescending(g => g.Guessed);
			return Ok(
				games.Select(
					g => new GameDTO
					{
						Id = g.Id,
						GameConfigAnimals = g.GameConfigAnimals,
						Nickname = g.Nickname,
						Points = g.Points,
						Guessed = g.Guessed,
						Duration = g.Duration
					}
				).ToList()
			);
		}

		//https://localhost:xxxx/game/twopairs
		[HttpGet]
		[Route("twopairs")]
		public async Task<IActionResult> GetGamesPlus2Guessed()
		{
			var games = await this.GameRepository.GetGamesPlus2Guessed();
			return Ok(
				games.Select(
					g => new GameDTO
					{
						Id = g.Id,
						GameConfigAnimals = g.GameConfigAnimals,
						Nickname = g.Nickname,
						Points = g.Points,
						Guessed = g.Guessed,
						Duration = g.Duration
					}
				).ToList()
			);
		}
	}
}
