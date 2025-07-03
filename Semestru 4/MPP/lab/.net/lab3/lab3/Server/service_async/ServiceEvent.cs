using Model.app.domain;
using Persistence.repo_async;
using Services.services_async;

namespace Server.app.service_async
{
	public class ServiceEvent : IServiceEvent
	{
		private readonly IEventRepository _eventRepository;

		public ServiceEvent(IEventRepository eventRepository)
		{
			_eventRepository = eventRepository;
		}

		public async Task<IEnumerable<Event>> GetAllAsync() =>
			 await _eventRepository.GetAllAsync();

		public async Task<Event?> GetByIdAsync(int id) =>
			 await _eventRepository.GetByIdAsync(id);

		public async Task<Event?> AddAsync(string name, int minAge, int maxAge)
		{
			var newEvent = new Event { Name = name, MinAge = minAge, MaxAge = maxAge };
			return await _eventRepository.CreateAsync(newEvent);
		}

		public async Task<Event?> UpdateAsync(Event event_) =>
			 await _eventRepository.UpdateAsync(event_);

		public async Task<IEnumerable<Event>> GetByMinAgeAsync(int minAge) =>
			 throw new NotImplementedException();

		public async Task<IEnumerable<Event>> GetByMaxAgeAsync(int maxAge) =>
			 throw new NotImplementedException();
	}
}
