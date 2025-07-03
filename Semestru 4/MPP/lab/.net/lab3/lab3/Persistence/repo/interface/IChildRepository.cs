using Model.app.domain;

namespace Persistence.app.repo.@interface
{
    public interface IChildRepository : IRepository<int, Child>
    {
        IEnumerable<Child> GetAllByAge(int age);
    }
}
