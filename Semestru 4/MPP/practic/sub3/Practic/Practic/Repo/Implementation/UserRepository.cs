using Practic.Data;
using Practic.Model.Domain;
using Practic.Repo.Interface;
using Microsoft.EntityFrameworkCore;
using Serilog;

namespace Practic.Repo.Implementation
{
	public class UserRepository : IUserRepository
	{
		private readonly ApplicationDbContext dbContext;

		public UserRepository(ApplicationDbContext dbContext)
		{
			this.dbContext = dbContext;
		}

		public async Task<User?> GetUserByNickname(string nickname)
		{
			Log.Information($"User {nickname} tries to login.\n");
			return await dbContext.Users.FirstOrDefaultAsync(user => user!.Nickname == nickname);
		}


		public async Task<User> AddUser(User user)
		{
			await dbContext.Users.AddAsync(user);
			await dbContext.SaveChangesAsync();
			return user;
		}
	}
}
