using AlegeLitere.Model.Domain;

namespace AlegeLitere.Repo.Interface
{
	public interface IUserRepository
	{
		Task<User?> GetUserByNickname(string nickname);
		Task<User> AddUser(User user);
	}
}
