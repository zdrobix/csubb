using Model.app.domain;

namespace Services.services
{
	public interface IServiceEvent
	{
		IEnumerable<Event> GetAll();
		Event? GetById(int id);
		Event? Add(string name, int minAge, int maxAge);
		Event? Update(Event event_);
		IEnumerable<Event>? GetByMinAge(int minAge);
		IEnumerable<Event>? GetByMaxAge(int maxAge);
	}
}
