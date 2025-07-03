using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using restAPI.Model.Domain;
using restAPI.Model.DTO;
using restAPI.Repo.Interface;

namespace restAPI.Controllers
{
	[Authorize]
	[Route("api/[controller]")]
	[ApiController]
	public class ChildController : ControllerBase
	{
		private readonly IChildRepository ChildRepository;

		public ChildController(IChildRepository childRepository)
		{
			ChildRepository = childRepository;
		}

		// GET: https://localhost:5000/api/child/{id}
		[HttpGet]
		[Route("{id:int}")]
		public async Task<IActionResult> GetChildById ([FromRoute]int id)
		{
			var child = await ChildRepository.GetByIdAsync(id);
			return child == null ? 
				NotFound() : 
				Ok(
					new ChildDTO
					{
						Id = child.Id,
						Name = child.Name,
						Cnp = child.Cnp
					}
			);
		}

		// POST : https://localhost:5000/api/child
		[HttpPost]
		public async Task<IActionResult> CreateChild([FromBody] AddChildRequestDTO request)
		{
			var child = new Child (request.Name, request.Cnp);
			child = await ChildRepository.CreateAsync(child);
			return Ok(
				new ChildDTO
				{
					Id = child.Id,
					Name = child.Name,
					Cnp = child.Cnp,
				}
			);
		}

		// GET : https://localhost:5000/api/child
		[HttpGet]
		public async Task<IActionResult> GetAllChilds()
		{
			var childs = await ChildRepository.GetAllAsync();
			return Ok(
				childs.Select(child => new ChildDTO
				{
					Id = child.Id,
					Name = child.Name,
					Cnp = child.Cnp,
				}).ToList()
			);
		}

		// PUT : https://localhost:5000/api/child/{id}
		[HttpPut]
		[Route("{id:int}")]
		public async Task<IActionResult> UpdateChild([FromRoute] int id, [FromBody] UpdateChildRequestDTO request)
		{
			var child = await ChildRepository.GetByIdAsync(id);
			if (child == null) return NotFound();

			child.Name = request.Name;
			child.Cnp = request.Cnp;

			child = await ChildRepository.UpdateAsync(child);
			return Ok(
				new ChildDTO
				{
					Id = child!.Id,
					Name = child.Name,
					Cnp = child.Cnp,
				}
			);
		}

		// DELETE : https://localhost:5000/api/child/{id}
		[HttpDelete]
		[Route("{id:int}")]
		public async Task<IActionResult> DeleteChild([FromRoute] int id)
		{
			var child = await ChildRepository.DeleteAsync(id);
			return child == null ? 
				NotFound() :
				Ok(
					new ChildDTO
					{
						Id = child.Id,
						Name = child.Name,
						Cnp = child.Cnp,
					}
			);
		}
	}
}
