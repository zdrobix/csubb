using lab1.Models.Domain;

namespace lab1.Repositories.Interface
{
	public interface IEventRepository : IRepository<int, Event>
	{
		Task<IEnumerable<Event>> GetAllByMinAge(int minAge);
		Task<IEnumerable<Event>> GetAllByMaxAge(int maxAge);
	}
}
