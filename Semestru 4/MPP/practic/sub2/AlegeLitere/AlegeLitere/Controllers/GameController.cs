using AlegeLitere.Model.DTO;
using AlegeLitere.Repo.Interface;
using Azure.Core;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace AlegeLitere.Controllers
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
					Attempts = request.Attempts,
					Points = request.Points,
					StartedAt = request.StartedAt
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
				Points = request.Points,
				StartedAt = request.StartedAt
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
						Won = g.Won,
						Points = g.Points,
						StartedAt = g.StartedAt
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
			games = games.OrderByDescending(g => g.Points);
			return Ok(
				games.Select(
					g => new GameDTO
					{
						Id = g.Id,
						GameConfigurationId = g.GameConfigurationId,
						Attempts = g.Attempts,
						Nickname = g.Nickname,
						Won = g.Won,
						Points = g.Points,
						StartedAt = g.StartedAt
					}
				).ToList()
			);
		}

		//https://localhost:xxxx/api/game/lost
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
						Won = g.Won,
						Points = g.Points,
						StartedAt = g.StartedAt
					}
				).ToList()
			);
		}

		//https://localhost:xxxx/api/game/similar
		[HttpGet]
		[Route("similar")]
		public async Task<IActionResult> GetSimilarGames()
		{
			var games = await this.GameRepository.GetSimilarGames();
			return Ok(
				games.Select(
					g => new GameDTO
					{
						Id = g.Id,
						GameConfigurationId = g.GameConfigurationId,
						Attempts = g.Attempts,
						Nickname = g.Nickname,
						Won = g.Won,
						Points = g.Points,
						StartedAt = g.StartedAt
					}
				).ToList()
			);
		}
	}
}
