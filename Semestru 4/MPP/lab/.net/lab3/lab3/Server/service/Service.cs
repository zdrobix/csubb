using Model.app.domain;
using Services.services;

namespace Server.app.service
{
	public class Service : IService
	{
		private IServiceChild ServiceChild;
		private IServiceEvent ServiceEvent;
		private IServiceSignup ServiceSignup;
		private IServiceLogin ServiceLogin;

		private Dictionary<LoginInfo, IObserver> loggedClients = new Dictionary<LoginInfo, IObserver>();

		public Service(IServiceChild serviceChild, IServiceEvent serviceEvent, IServiceSignup serviceSignup, IServiceLogin serviceLogin)
		{
			this.ServiceChild = serviceChild;
			this.ServiceEvent = serviceEvent;
			this.ServiceSignup = serviceSignup;
			this.ServiceLogin = serviceLogin;
		}

		public Child? AddChild(string name, string cnp)
		{
			Console.WriteLine(string.Join(", ", this.loggedClients.Keys.Select(k => $"{k.Id}) {k.Username}").ToList()));

			var child = this.ServiceChild.Add(name, cnp);
			if (child != null)
			{
				NotifyChildAdded(child);
			}
			return child;
		}
		public Event? AddEvent(string name, int minAge, int maxAge)
		{
			var event_ = this.ServiceEvent.Add(name, minAge, maxAge);
			if (event_ != null)
			{
				NotifyEventAdded(event_);
			}
			return event_;
		}

		public Signup? AddSignup(Child child, Event event_)
		{
			var signup = this.ServiceSignup.Add(child, event_);
			if (signup != null)
			{
				NotifySignupAdded(signup);
			}
			return signup;
		}

		public IEnumerable<Child> GetAllChildren() =>
			this.ServiceChild.GetAll();

		public IEnumerable<Event> GetAllEvents() =>
			this.ServiceEvent.GetAll();

		public IEnumerable<Signup> GetAllSignups() =>
			this.ServiceSignup.GetAll();

		public LoginInfo GetLoginByUsername(string username) =>
			this.ServiceLogin.GetByUsername(username);

		public IEnumerable<Child>? GetChildByAge(int age) =>
			this.ServiceChild.GetByAge(age);
		public Child? GetChildById(int id) =>
			this.ServiceChild.GetById(id);
		public Event? GetEventById(int id) =>
			this.ServiceEvent.GetById(id);

		public IEnumerable<Signup>? GetSignupByChild(int childId) =>
			this.ServiceSignup.GetByChild(childId);

		public IEnumerable<Signup>? GetSignupByEvent(int eventId) =>
			this.ServiceSignup.GetByEvent(eventId);
		public Signup? GetSignupById(int childId, int eventId) =>
			this.ServiceSignup.GetById(new Tuple<int, int>(childId, eventId));

		public Child? UpdateChild(Child child) =>
			this.ServiceChild.Update(child);

		public Event? UpdateEvent(Event event_) =>
			this.ServiceEvent.Update(event_);

		public bool Login(LoginInfo login, IObserver client)
		{
			if (this.loggedClients.Keys.Any(l => l.Username == login.Username))
				return false;

			this.loggedClients[login] = client;
			return true;
		}

		public bool Logout(LoginInfo login, IObserver client)
		{
			if (!this.loggedClients.Keys.Any(l => l.Username == login.Username))
			{
				Console.WriteLine($"User {login.Username} not found in logged clients.");
				return false;
			}
			Console.WriteLine($"{login.Username}({login.Id}) is logging out.");
			this.loggedClients.Remove(login);
			return true;
		}

		public IEnumerable<Event>? GetEventByMinAge(int minAge) =>
			this.ServiceEvent.GetByMinAge(minAge);

		public IEnumerable<Event>? GetEventByMaxAge(int maxAge) =>
			this.ServiceEvent.GetByMaxAge(maxAge);

		//TO DO
		private void NotifyChildAdded(Child child)
		{
			foreach (var client in loggedClients)
			{
				Console.WriteLine($"Notifying {client.Key} of {child}");
				Task.Run(() =>
				{
					client.Value.ChildAdded(child);
				});
			}
		}
		private void NotifyEventAdded(Event event_) 
		{
			foreach (var client in loggedClients)
			{
				Console.WriteLine($"Notifying {client.Key} of {event_}");
				Task.Run(() =>
				{
					client.Value.EventAdded(event_);
				});
			}
		}
		private void NotifySignupAdded(Signup signup) 
		{
			foreach (var client in loggedClients)
			{
				Console.WriteLine($"Notifying {client.Key} of {signup}");
				Task.Run(() =>
				{
					client.Value.SignupAdded(signup);
				});
			}
		}
	}
}
