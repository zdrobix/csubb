using Model.app.domain;

namespace Services.services
{
    public interface IServiceSignup
    {
        IEnumerable<Signup> GetAll();
        Signup? GetById(Tuple<int, int> id);
        Signup? Add(Child child, Event event_);
        Signup? Update(Signup signup);
        IEnumerable<Signup>? GetByEvent(int eventId);
        IEnumerable<Signup>? GetByChild(int childId);
    }
}
