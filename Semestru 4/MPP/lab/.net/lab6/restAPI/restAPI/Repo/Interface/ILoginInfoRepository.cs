using restAPI.Model.Domain;

namespace restAPI.Repo.Interface
{
	public interface ILoginInfoRepository
	{
		Task<LoginInfo> CreateAsync(LoginInfo loginInfo);
		Task<LoginInfo?> GetByUsernameAsync(string username);
		Task<IEnumerable<LoginInfo>> GetAllAsync();
		Task<LoginInfo?> UpdateAsync(LoginInfo loginInfo);
		Task<LoginInfo?> DeleteAsync(int id);
	}
}
