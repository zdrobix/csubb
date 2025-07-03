using lab1.Models.Domain;

namespace lab1.Repositories.Interface
{
	public interface IRepository<ID, E> where E : Entity<ID>
	{
		Task<E> CreateAsync(E e);
		Task<IEnumerable<E>> GetAllAsync();
		Task<E?> GetById(ID id);
		Task<E?> UpdateAsync(E e);
		Task<E?> DeleteAsync(ID id);
	}
}
