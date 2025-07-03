using lab1.Models.Domain;
using lab1.Models.Dto;
using lab1.Repositories.Interface;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace lab1.Controllers
{
	[Route("api/[controller]")]
	[ApiController]
	public class ChildController : ControllerBase
	{
		private readonly IChildRepository childRepository;

		public ChildController(IChildRepository childRepository)
		{
			this.childRepository = childRepository;
		}


		// POST
		[HttpPost]
		public async Task<IActionResult> CreateChild([FromBody] CreateChildRequestDto request)
		{
			var child = new Child(request.Name, request.Cnp);
			await childRepository.CreateAsync(child);
			return Ok(
				(ChildDto)new ChildDto
				{
					Name = child.Name,
					Cnp = child.Cnp
				}.SetId(child.Id)
			);
		}

		// GET
		[HttpGet]
		public async Task<IActionResult> GetChildren()
		{
			var children = await childRepository.GetAllAsync();
			return Ok(children.Select(child =>
			(ChildDto)new ChildDto
			{
				Name = child.Name,
				Cnp = child.Cnp
			}.SetId(child.Id)));
		}

		// GET
		[HttpGet("{id:int}")]
		public async Task<IActionResult> GetChildByID([FromRoute] int id)
		{
			var child = await childRepository.GetById(id);
			return child == null ?
				NotFound() : Ok(
					(ChildDto)new ChildDto
					{
						Name = child.Name,
						Cnp = child.Cnp
					}.SetId(child.Id));

		}
	}
}
