using Model.app.domain;

namespace Services.services_async
{
	public interface IServiceEvent
	{
		Task<IEnumerable<Event>> GetAllAsync();
		Task<Event?> GetByIdAsync(int id);
		Task<Event?> AddAsync(string name, int minAge, int maxAge);
		Task<Event?> UpdateAsync(Event event_);
		Task<IEnumerable<Event>> GetByMinAgeAsync(int minAge);
		Task<IEnumerable<Event>> GetByMaxAgeAsync(int maxAge);
	}
}
