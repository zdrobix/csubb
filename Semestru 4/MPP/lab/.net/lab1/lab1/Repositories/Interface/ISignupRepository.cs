using lab1.Models.Domain;

namespace lab1.Repositories.Interface
{
	public interface ISignupRepository : IRepository<Tuple<int, int>, Signup>
	{
		Task<IEnumerable<Signup>> GetAllByChildId(int id);
		Task<IEnumerable<Signup>> GetAllByEventId(int id);
	}
}
