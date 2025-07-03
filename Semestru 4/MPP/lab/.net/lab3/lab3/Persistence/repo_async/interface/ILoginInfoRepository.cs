using Model.app.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistence.repo_async
{
	public interface ILoginInfoRepository
	{
		Task<LoginInfo> CreateAsync(LoginInfo login);
		Task<IEnumerable<LoginInfo>> GetAllAsync();
		Task<LoginInfo?> GetByIdAsync(int id);
		Task<LoginInfo?> GetByUsernameAsync(string username);
		Task<LoginInfo?> UpdateAsync(LoginInfo login);
		Task<LoginInfo?> DeleteAsync(int id);

	}
}
