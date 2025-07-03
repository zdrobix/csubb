using Model.app.domain;
using System;

namespace Services.services
{
	public interface IService
	{
		IEnumerable<Child> GetAllChildren();
		Child? GetChildById(int id);
		Child? AddChild(String name, String cnp);
		Child? UpdateChild(Child child);
		IEnumerable<Child>? GetChildByAge(int age);
		IEnumerable<Event> GetAllEvents();
		Event? GetEventById(int id);
		Event? AddEvent(String name, int minAge, int maxAge);
		Event? UpdateEvent(Event event_);
		IEnumerable<Event>? GetEventByMinAge(int minAge);
		IEnumerable<Event>? GetEventByMaxAge(int maxAge);
		LoginInfo GetLoginByUsername (String username);
		IEnumerable<Signup> GetAllSignups();
		Signup? AddSignup(Child child, Event event_);
		Signup? GetSignupById (int childId, int eventId);
		IEnumerable<Signup>? GetSignupByEvent(int eventId);
		IEnumerable<Signup>? GetSignupByChild(int childId);
		bool Login(LoginInfo login, IObserver client);
		bool Logout(LoginInfo login, IObserver client);
	}
}
