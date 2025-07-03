using Model.app.domain;

namespace Persistence.app.repo.@interface
{
	public interface ILoginRepository : IRepository<int, LoginInfo>
	{
		LoginInfo GetByUsername(string username);
	}
}
