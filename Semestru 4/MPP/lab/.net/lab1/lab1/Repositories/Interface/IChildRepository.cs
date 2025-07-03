using lab1.Models.Domain;

namespace lab1.Repositories.Interface
{
	public interface IChildRepository : IRepository<int, Child>
	{
		Task<IEnumerable<Child>> GetAllByAge(int age);
	}
}
