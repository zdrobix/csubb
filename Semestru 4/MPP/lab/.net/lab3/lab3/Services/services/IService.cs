using Model.app.domain;
using System;

namespace Services.services_async
{
	public interface IService
	{
		Task<Child?> AddChild(string name, string cnp);
		Task<Event?> AddEvent(string name, int minAge, int maxAge);
		Task<Signup?> AddSignup(Child child, Event event_);

		Task<IEnumerable<Child>> GetAllChildren();
		Task<IEnumerable<Event>> GetAllEvents();
		Task<IEnumerable<Signup>> GetAllSignups();

		Task<LoginInfo?> GetLoginByUsername(string username);
		Task<IEnumerable<Child>> GetChildByAge(int age);
		Task<Child?> GetChildById(int id);
		Task<Event?> GetEventById(int id);
		Task<IEnumerable<Signup>> GetSignupByChild(int childId);
		Task<IEnumerable<Signup>> GetSignupByEvent(int eventId);
		Task<Signup?> GetSignupByChildIdAndEventId(int childId, int eventId);

		Task<Child?> UpdateChild(Child child);
		Task<Event?> UpdateEvent(Event event_);

		Task<IEnumerable<Event>> GetEventByMinAge(int minAge);
		Task<IEnumerable<Event>> GetEventByMaxAge(int maxAge);

		bool Login(LoginInfo login, IObserver client);
		bool Logout(LoginInfo login, IObserver client);


	}
}
