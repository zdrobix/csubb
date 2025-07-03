using Practic.Model.DTO;
using Practic.Repo.Interface;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Serilog;

namespace Practic.Controllers
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
					Animals = gameConfig.Animals,
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
					Animals = gameConfig.Animals,
				}
			);
		}

		//https://localhost:xxxx/api/gameconfig
		[HttpPost]
		public async Task<IActionResult> AddGameConfig([FromBody] AddGameConfigRequest request)
		{
			var gameConfig = await this.GameConfigRepository.AddGameConfig(new Model.Domain.GameConfig
			{
				Animals = request.Animals,
			});

			return Ok(new GameConfigDTO
			{
				Id = gameConfig.Id,
				Animals = request.Animals,
			});
		}

		//https://localhost:xxxx/api/gameconfig/$id
		[HttpPut]
		[Route("{id:int}")]
		public async Task<IActionResult> UpdateGameConfig([FromRoute] int id, [FromBody] AddGameConfigRequest request)
		{
			var existing = await this.GameConfigRepository.GetGameConfig(id);
			Log.Information("trying to update game config" + id);

			if (existing == null)
			{
				Log.Information("cannot find game config " + id);
				return NotFound();
			}
			var gameConfig = this.GameConfigRepository.UpdateGameConfig(new Model.Domain.GameConfig
			{
				Id = id,
				Animals = request.Animals,
			});
			Log.Information($"updated game config {request.Animals} " + id);
			return Ok(new GameConfigDTO
			{
				Id = id,
				Animals = request.Animals,
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
					Animals = x.Animals,
				}
			));
		}
	}
}
