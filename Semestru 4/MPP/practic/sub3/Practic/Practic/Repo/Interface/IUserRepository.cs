using Practic.Model.Domain;

namespace Practic.Repo.Interface
{
	public interface IUserRepository
	{
		Task<User?> GetUserByNickname(string nickname);
		Task<User> AddUser(User user);
	}
}
