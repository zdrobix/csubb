using Microsoft.EntityFrameworkCore;
using restAPI.Data;
using restAPI.Model.Domain;
using restAPI.Repo.Interface;

namespace restAPI.Repo.Implementation
{
	public class LoginInfoRepository : ILoginInfoRepository
	{
		private readonly AppDbContext DbContext;

		public LoginInfoRepository(AppDbContext dbContext)
		{
			DbContext = dbContext;
		}

		public async Task<LoginInfo> CreateAsync(LoginInfo signup_)
		{
			await DbContext.Logins.AddAsync(signup_);
			await DbContext.SaveChangesAsync();
			return signup_;
		}

		public async Task<LoginInfo?> DeleteAsync(int id)
		{
			var existingLoginInfo = await DbContext.Logins.FirstOrDefaultAsync(e => e.Id == id);
			if (existingLoginInfo == null)
			{
				return null;
			}
			DbContext.Logins.Remove(existingLoginInfo);
			await DbContext.SaveChangesAsync();
			return existingLoginInfo;
		}

		public async Task<IEnumerable<LoginInfo>> GetAllAsync() =>
			await DbContext.Logins.ToListAsync();

		public async Task<LoginInfo?> GetByUsernameAsync(string username) =>
			await DbContext.Logins.FirstOrDefaultAsync(e => e.Username == username);

		public async Task<LoginInfo?> UpdateAsync(LoginInfo signup_)
		{
			var existingLoginInfo = await DbContext.Logins.FirstOrDefaultAsync(e => e.Id == signup_.Id);
			if (existingLoginInfo == null)
			{
				return null;
			}
			DbContext.Entry(existingLoginInfo).CurrentValues.SetValues(signup_);
			await DbContext.SaveChangesAsync();
			return existingLoginInfo;
		}
	}
}
