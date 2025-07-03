using Model.app.domain;
using Persistence.data;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistence.repo_async.implementation
{
	public class LoginInfoRepository : ILoginInfoRepository
	{
		private readonly AppDbContext DbContext;

		public LoginInfoRepository(AppDbContext dbContext)
		{
			DbContext = dbContext;
		}

		public async Task<LoginInfo> CreateAsync(LoginInfo login)
		{
			await DbContext.Logins.AddAsync(login);
			await DbContext.SaveChangesAsync();
			return login;
		}

		public Task<LoginInfo?> DeleteAsync(int id)
		{
			throw new NotImplementedException();
		}

		public Task<IEnumerable<LoginInfo>> GetAllAsync()
		{
			throw new NotImplementedException();
		}

		public Task<LoginInfo?> GetByIdAsync(int id)
		{
			throw new NotImplementedException();
		}

		public async Task<LoginInfo?> GetByUsernameAsync(string username) =>
			await DbContext.Logins.FirstOrDefaultAsync(l => l.Username == username);

		public Task<LoginInfo?> UpdateAsync(LoginInfo login)
		{
			throw new NotImplementedException();
		}
	}
}
