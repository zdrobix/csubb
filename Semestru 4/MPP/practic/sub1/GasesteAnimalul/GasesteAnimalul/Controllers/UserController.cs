using GasesteAnimalul.Model.DTO;
using GasesteAnimalul.Repo.Interface;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace GasesteAnimalul.Controllers
{
	[Route("api/[controller]")]
	[ApiController]
	public class UserController : ControllerBase
	{
		public IUserRepository UserRepository { get; set; }
		public UserController(IUserRepository userRepository)
		{
			UserRepository = userRepository;
		}

		//https://localhost:xxxx/api/user/login
		[HttpPost]
		[Route("login")]
		public async Task<IActionResult> GetUserByNickname([FromBody] GetUserRequest request)
		{
			var user = await this.UserRepository.GetUserByNickname(request.Nickname);
			if (user == null)
			{
				return NotFound();
			}
			return Ok(
				new UserDTO
				{
					Id = user.Id,
					Nickname = user.Nickname,
				}	
			);
		}

		[HttpPost]
		public async Task<IActionResult> AddUser([FromBody] AddUserRequest request)
		{
			var user = await this.UserRepository.AddUser(new Model.Domain.User { Nickname = request.Nickname});

			if (user == null)
			{
				return NotFound();
			}
			return Ok(
				new UserDTO
				{
					Id = user.Id,
					Nickname = user.Nickname,
				}
			);
		}
		
	}
}
