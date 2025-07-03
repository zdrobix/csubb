using restAPI.Model.Domain;

namespace restAPI.Repo.Interface
{
	public interface ISignupRepository
	{
		Task<Signup> CreateAsync(Signup signup);
		Task<Signup?> GetByIdAsync(int childId, int eventId);
		Task<IEnumerable<Signup>> GetAllAsync();
		Task<Signup?> UpdateAsync(Signup signup);
		Task<Signup?> DeleteAsync(int childId, int eventId);
	}
}
