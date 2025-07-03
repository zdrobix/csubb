using restAPI.Model.Domain;

namespace restAPI.Repo.Interface
{
	public interface IEventRepository
	{
		Task<Event> CreateAsync(Event event_);
		Task<Event?> GetByIdAsync(int id);
		Task<IEnumerable<Event>> GetAllAsync();
		Task<Event?> UpdateAsync(Event event_);
		Task<Event?> DeleteAsync(int id);
	}
}
