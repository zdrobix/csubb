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
	public class EventController : ControllerBase
	{
		private readonly IEventRepository EventRepository;

		public EventController(IEventRepository eventRepository)
		{
			EventRepository = eventRepository;
		}

		// GET: https://localhost:5000/api/event/{id}
		[HttpGet]
		[Route("{id:int}")]
		public async Task<IActionResult> GetEventById ([FromRoute]int id)
		{
			var event_ = await EventRepository.GetByIdAsync(id);
			return event_ == null ? 
				NotFound() : 
				Ok(
					new EventDTO
					{
						Id = event_.Id,
						Name = event_.Name,
						MinAge = event_.MinAge,
						MaxAge = event_.MaxAge
					}
			);
		}

		// POST : https://localhost:5000/api/event
		[HttpPost]
		public async Task<IActionResult> CreateEvent([FromBody] AddEventRequestDTO request)
		{
			var event_ = new Event (request.Name, request.MinAge, request.MaxAge);
			event_ = await EventRepository.CreateAsync(event_);
			return Ok(
				new EventDTO
				{
					Id = event_.Id,
					Name = event_.Name,
					MinAge = event_.MinAge,
					MaxAge = event_.MaxAge
				}
			);
		}

		// GET : https://localhost:5000/api/event
		[HttpGet]
		public async Task<IActionResult> GetAllEvents()
		{
			var events = await EventRepository.GetAllAsync();
			return Ok(
				events.Select(event_ => new EventDTO
				{
					Id = event_.Id,
					Name = event_.Name,
					MinAge = event_.MinAge,
					MaxAge = event_.MaxAge
				}).ToList()
			);
		}

		// PUT : https://localhost:5000/api/event/{id}
		[HttpPut]
		[Route("{id:int}")]
		public async Task<IActionResult> UpdateEvent([FromRoute] int id, [FromBody] UpdateEventRequestDTO request)
		{
			var event_ = await EventRepository.GetByIdAsync(id);
			if (event_ == null) return NotFound();

			event_.Name = request.Name;
			event_.MinAge = request.MinAge;
			event_.MaxAge = request.MaxAge;

			event_ = await EventRepository.UpdateAsync(event_);
			return Ok(
				new EventDTO
				{
					Id = event_!.Id,
					Name = event_.Name,
					MinAge = event_.MinAge,
					MaxAge = event_.MaxAge
				}
			);
		}

		// DELETE : https://localhost:5000/api/event/{id}
		[HttpDelete]
		[Route("{id:int}")]
		public async Task<IActionResult> DeleteEvent([FromRoute] int id)
		{
			var event_ = await EventRepository.DeleteAsync(id);
			return event_ == null ? 
				NotFound() :
				Ok(
					new EventDTO
					{
						Id = event_.Id,
						Name = event_.Name,
						MinAge = event_.MinAge,
						MaxAge = event_.MaxAge
					}
			);
		}
	}
}
