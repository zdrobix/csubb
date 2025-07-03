using Model.app.domain;

namespace Services.services
{
	public interface IObserver
	{
		void ChildAdded(Child child);
		void EventAdded(Event event_);
		void SignupAdded(Signup signup);
	}
}
