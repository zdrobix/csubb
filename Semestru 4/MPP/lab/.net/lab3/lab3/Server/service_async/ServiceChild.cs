using Model.app.domain;
using Persistence.repo_async;
using Services.services_async;

namespace Server.app.service_async
{
	public class ServiceChild : IServiceChild
	{
		private IChildRepository Repo;

		public ServiceChild(IChildRepository repo) =>
			this.Repo = repo;

		public async Task<Child> AddAsync(string name, string cnp) =>
			await this.Repo.CreateAsync(new Child(name, cnp));

		public async Task<IEnumerable<Child>> GetAllAsync() =>
			await this.Repo.GetAllAsync();

		public async Task<IEnumerable<Child>?> GetByAgeAsync(int age) =>
			throw new NotImplementedException();

		public async Task<Child?> GetByIdAsync(int id) =>
			await this.Repo.GetByIdAsync(id);

		public async Task<Child?> UpdateAsync(Child child) =>
			await this.Repo.UpdateAsync(child);
	}
}
