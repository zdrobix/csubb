using Model.app.domain;

namespace Persistence.app.repo.@interface
{
    public interface ISignupRepository : IRepository<Tuple<int, int>, Signup>
    {
        IEnumerable<Signup> GetAllByChildId(int id);
        IEnumerable<Signup> GetAllByEventId(int id);
        IEnumerable<Signup> GetAllJoin();
	}
}
