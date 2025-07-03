using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using restAPI.Model.Domain;
using restAPI.Model.DTO;
using restAPI.Repo.Interface;

namespace restAPI.Controllers
{
	[Authorize]
	[Route("api/[controller]")]
	[ApiController]
	public class SignupController : ControllerBase
	{
		private readonly ISignupRepository SignupRepository;

		public SignupController(ISignupRepository signupRepository)
		{
			this.SignupRepository = signupRepository; 
		}

		// GET : https://localhost:5000/api/signup
		[HttpGet]
		public async Task<IActionResult> GetAllSignups()
		{
			var signups = await this.SignupRepository.GetAllAsync();
			return Ok(
				signups.Select(s => new SignupDTO
				{
					ChildId = s.ChildId,
					EventId = s.EventId
				})
			);
		}

		// POST : https:/localhost:5000/api/signup
		[HttpPost]
		public async Task<IActionResult> CreateSignup([FromBody] AddSignupRequestDTO request)
		{
			var signup = new Signup(request.ChildId, request.EventId);
			signup = await this.SignupRepository.CreateAsync(signup);

			return Ok(
				new SignupDTO { 
					ChildId = signup.ChildId,
					EventId = signup.EventId
				}
			);
		}
	}
}
