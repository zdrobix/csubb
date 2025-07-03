using Model.app.domain;

namespace Persistence.app.repo.@interface
{
    public interface IRepository<ID, E> where E : Entity<ID>
    {
		E Create(E e);
        IEnumerable<E> GetAll();
        E? GetById(ID id);
        E? Update(E e);
        E? Delete(ID id);
    }
}
