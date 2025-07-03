using Model.app.domain;

namespace Services.services_async
{
	public interface IObserver
	{
		void ChildAdded(Child child);
		void EventAdded(Event event_);
		void SignupAdded(Signup signup);
	}
}
