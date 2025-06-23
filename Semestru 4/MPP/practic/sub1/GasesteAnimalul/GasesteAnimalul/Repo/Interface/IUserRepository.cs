using GasesteAnimalul.Model.Domain;

namespace GasesteAnimalul.Repo.Interface
{
	public interface IUserRepository
	{
		Task<User?> GetUserByNickname(string nickname);
		Task<User> AddUser(User user);
	}
}
