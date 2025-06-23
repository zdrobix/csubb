using AlegeLitere.Model.DTO;
using AlegeLitere.Repo.Interface;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace AlegeLitere.Controllers
{
	[Route("api/[controller]")]
	[ApiController]
	public class GameConfigController : ControllerBase
	{
		public IGameConfigRepository GameConfigRepository { get; set; }

		public GameConfigController(IGameConfigRepository gameConfigRepository)
		{
			GameConfigRepository = gameConfigRepository;
		}

		//https://localhost:xxxx/gameconfig/api/gameconfig
		[HttpGet]
		public async Task<IActionResult> GetRandomGameConfig ()
		{
			var gameConfig = await this.GameConfigRepository.GetRandomGameConfig();

			return Ok(
				new GameConfigDTO
				{
					Id = gameConfig.Id,
					Letters = gameConfig.Letters,
					Points = gameConfig.Points
				}
			);
		}

		//https://localhost:xxxx/gameconfig/api/gameconfig/{$id}
		[HttpGet]
		[Route("{id:int}")]
		public async Task<IActionResult> GetGameConfig([FromRoute] int id)
		{
			var gameConfig = await this.GameConfigRepository.GetGameConfig(id);

			if (gameConfig == null) return NotFound();

			return Ok(
				new GameConfigDTO
				{
					Id = gameConfig.Id,
					Letters = gameConfig.Letters,
					Points = gameConfig.Points
				}
			);
		}

		//https://localhost:xxxx/api/gameconfig
		[HttpPost]
		public async Task<IActionResult> AddGameConfig([FromBody] AddGameConfigRequest request)
		{
			var gameConfig = await this.GameConfigRepository.AddGameConfig(new Model.Domain.GameConfig
			{
				Letters = request.Letters,
				Points = request.Points
			});

			return Ok(new GameConfigDTO
			{
				Id = gameConfig.Id,
				Letters = request.Letters,
				Points = request.Points
			});
		}

		//https://localhost:xxxx/api/gameconfig
		[HttpPut]
		[Route("{id:int}")]
		public async Task<IActionResult> UpdateGameConfig([FromRoute] int id, [FromBody] AddGameConfigRequest request)
		{
			var existing = await this.GameConfigRepository.GetGameConfig(id);
			
			if (existing == null) return NotFound();
			var gameConfig = this.GameConfigRepository.UpdateGameConfig(new Model.Domain.GameConfig
			{
				Id = id,
				Letters = request.Letters,
				Points = request.Points
			});
			return Ok(new GameConfigDTO
			{
				Id = id,
				Letters = request.Letters,
				Points = request.Points
			});
		}

		//https://localhost:xxxx/api/gameconfig/all
		[HttpGet]
		[Route("all")]
		public async Task<IActionResult> GetAll () {
			var gameConfigs = await this.GameConfigRepository.GetAll();
			return Ok(gameConfigs.Select(
				x => new GameConfigDTO
				{
					Id = x.Id,
					Letters = x.Letters,
					Points = x.Points
				}
			));
		}
	}
}
