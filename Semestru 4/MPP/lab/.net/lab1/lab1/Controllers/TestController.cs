using lab1.Repositories.Interface;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace lab1.Controllers
{
	[Route("api/[controller]")]
	[ApiController]
	public class TestController : ControllerBase
	{

		private readonly IChildRepository ChildRepository;

		public TestController(IChildRepository childRepository)
		{
			this.ChildRepository = childRepository;
		}
	}
}
