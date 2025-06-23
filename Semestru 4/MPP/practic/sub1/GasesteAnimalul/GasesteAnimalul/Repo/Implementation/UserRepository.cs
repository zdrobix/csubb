using GasesteAnimalul.Data;
using GasesteAnimalul.Model.Domain;
using GasesteAnimalul.Repo.Interface;
using Microsoft.EntityFrameworkCore;

namespace GasesteAnimalul.Repo.Implementation
{
	public class UserRepository : IUserRepository
	{
		private readonly ApplicationDbContext dbContext;

		public UserRepository(ApplicationDbContext dbContext)
		{
			this.dbContext = dbContext;
		}

		public async Task<User?> GetUserByNickname(string nickname) =>
			await dbContext.Users.FirstOrDefaultAsync(user => user!.Nickname == nickname);


		public async Task<User> AddUser(User user)
		{
			await dbContext.Users.AddAsync(user);
			await dbContext.SaveChangesAsync();
			return user;
		}
	}
}
