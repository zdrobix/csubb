using Model.app.domain;
using Persistence.app.repo.@interface;
using Services.services;

namespace Server.app.service
{
	public class ServiceEvent : IServiceEvent
	{
		private IEventRepository Repo;

		public ServiceEvent(IEventRepository repo) =>
			this.Repo = repo;

		public IEnumerable<Event> GetAll() =>
			this.Repo.GetAll();

		public Event? GetById(int id) =>
			this.Repo.GetById(id);

		public Event? Update(Event event_) =>
			this.Repo.Update(event_);

		public IEnumerable<Event>? GetByMinAge(int minAge) =>
			this.Repo.GetAllByMinAge(minAge);

		public IEnumerable<Event>? GetByMaxAge(int maxAge) =>
			this.Repo.GetAllByMaxAge(maxAge);

		public Event? Add(string name, int minAge, int maxAge) => 
			this.Repo.Create(new Event(name, minAge, maxAge));
	}
}
