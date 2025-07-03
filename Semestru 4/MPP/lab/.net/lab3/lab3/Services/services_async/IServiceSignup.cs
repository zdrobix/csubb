using Model.app.domain;

namespace Services.services_async
{
    public interface IServiceSignup
    {
        Task<IEnumerable<Signup>> GetAll();
        Task<Signup?> GetByChildIdAndEventId(int childId, int eventId);
        Task<Signup?> Add(Child child, Event event_);
        Task<Signup?> Update(Signup signup);
        Task<IEnumerable<Signup>?> GetByEvent(int eventId);
        Task<IEnumerable<Signup>?> GetByChild(int childId);
    }
}
