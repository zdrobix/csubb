using restAPI.Model.Domain;

namespace restAPI.Repo.Interface
{
	public interface IChildRepository
	{
		Task<Child> CreateAsync(Child child);
		Task<Child?> GetByIdAsync(int id);
		Task<IEnumerable<Child>> GetAllAsync();
		Task<Child?> UpdateAsync(Child child);
		Task<Child?> DeleteAsync(int id);
	}
}
