using Model.app.domain;
using Persistence.app.repo.@interface;
using Services.services;

namespace Server.app.service
{
	public class ServiceSignup : IServiceSignup
	{
		private ISignupRepository Repo;

		public ServiceSignup(ISignupRepository repo) =>
			this.Repo = repo;

		public IEnumerable<Signup> GetAll() =>
			this.Repo.GetAllJoin();

		public Signup? GetById(Tuple<int, int> id) =>
			this.Repo.GetById(id);

		public IEnumerable<Signup>? GetByChild(int id) =>
			this.Repo.GetAllByChildId(id);

		public IEnumerable<Signup>? GetByEvent(int id) =>
			this.Repo.GetAllByEventId(id);

		public Signup? Add (Child child, Event event_) =>
			this.Repo.Create(new Signup(child, event_));

		public Signup? Update(Signup signup) => null!;
	}
}
