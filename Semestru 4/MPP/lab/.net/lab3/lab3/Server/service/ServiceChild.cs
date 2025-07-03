using Model.app.domain;
using Persistence.app.repo.@interface;
using Services.services;

namespace Server.app.service
{
	public class ServiceChild : IServiceChild
	{
		private IChildRepository Repo;

		public ServiceChild(IChildRepository repo) =>
			this.Repo = repo;

		public IEnumerable<Child> GetAll() =>
			this.Repo.GetAll();

		public Child? GetById(int id) =>
			this.Repo.GetById(id);

		public Child? Update(Child child) =>
			this.Repo.Update(child);

		public IEnumerable<Child> GetByAge(int age) => 
			this.Repo.GetAllByAge(age);

		public Child Add(string name, string cnp) => this.Repo.Create(new Child(name, cnp));

	}
}
