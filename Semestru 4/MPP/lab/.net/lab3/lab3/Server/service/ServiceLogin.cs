using Model.app.domain;
using Persistence.app.repo.@interface;
using Services.services;

namespace Server.app.service
{
	public class ServiceLogin : IServiceLogin
	{
		private ILoginRepository Repo;

		public ServiceLogin(ILoginRepository repo)
		{
			this.Repo = repo;
		}

		public LoginInfo GetByUsername(string username) =>
			this.Repo.GetByUsername(username);
	}
}
