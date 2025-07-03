using Model.app.domain;

namespace Persistence.app.repo.@interface
{
    public interface IEventRepository : IRepository<int, Event>
    {
        IEnumerable<Event> GetAllByMinAge(int minAge);
        IEnumerable<Event> GetAllByMaxAge(int maxAge);
    }
}
