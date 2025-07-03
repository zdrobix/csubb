using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PharmacyApi.Utils;
using restAPI.Model.DTO;
using restAPI.Repo.Interface;
using restAPI.Services.Interface;

namespace restAPI.Controllers
{
	[Route("api/[controller]")]
	[ApiController]
	public class LoginInfoController : ControllerBase
	{
		private ILoginInfoRepository LoginInfoRepository{ get; set; }
		private readonly ITokenService TokenService;

		public LoginInfoController(ILoginInfoRepository loginInfoRepository, ITokenService tokenService)
		{
			this.LoginInfoRepository = loginInfoRepository;
			this.TokenService = tokenService;
		}

		// POST : https://localhost:5000/api/logininfo/{login}
		[HttpPost]
		[Route("login")]
		public async Task<IActionResult> ValidateLogin([FromBody] LoginRequestDTO request)
		{
			var loginInfo = await this.LoginInfoRepository.GetByUsernameAsync(request.Username);

			if (loginInfo == null)
				return NotFound();

			if (PasswordHasher.Encrypt(request.Password) != loginInfo.Password)
				return Unauthorized();

			var token = this.TokenService.GenerateToken(loginInfo);
			return Ok(new { token });
		}

		// POST : https://localhost:5000/api/logininfo
		[Authorize]
		[HttpPost]
		public async Task<IActionResult> CreateLogin([FromBody] LoginAddRequestDTO request)
		{
			var loginInfo = await this.LoginInfoRepository.GetByUsernameAsync(request.Username);

			if (loginInfo != null)
				return BadRequest();

			var addedLogin = await this.LoginInfoRepository.CreateAsync(
				new Model.Domain.LoginInfo(
					request.Username,
					PasswordHasher.Encrypt(request.Password)));

			return Ok(/*bla bla bla*/);
		}
	}
}
