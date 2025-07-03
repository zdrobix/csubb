using Model.app.domain;
using Persistence.repo_async;
using Services.services_async;

namespace Server.app.service_async
{
	public class ServiceLogin : IServiceLogin
	{
		private ILoginInfoRepository Repo;

		public ServiceLogin(ILoginInfoRepository repo)
		{
			this.Repo = repo;
		}

		public async Task<LoginInfo?> GetByUsernameAsync(string username) =>
			await this.Repo.GetByUsernameAsync(username);
	}
}
