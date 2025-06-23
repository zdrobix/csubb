using GasesteAnimalul.Model.DTO;
using GasesteAnimalul.Repo.Interface;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace GasesteAnimalul.Controllers
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
					Animal = gameConfig.Animal,
					XCoord = gameConfig.XCoord,
					YCoord = gameConfig.YCoord
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
					Animal = gameConfig.Animal,
					XCoord = gameConfig.XCoord,
					YCoord = gameConfig.YCoord
				}
			);
		}

		//https://localhost:xxxx/gameconfig/api
		[HttpPost]
		public async Task<IActionResult> AddGameConfig([FromBody] AddGameConfigRequest request)
		{
			var gameConfig = await this.GameConfigRepository.AddGameConfig(new Model.Domain.GameConfig
			{
				Animal = request.Animal,
				XCoord = request.XCoord,
				YCoord = request.YCoord
			});

			return Ok(new GameConfigDTO
			{
				Id = gameConfig.Id,
				Animal = gameConfig.Animal,
				XCoord = gameConfig.XCoord,
				YCoord = gameConfig.YCoord
			});
		}
	}
}
