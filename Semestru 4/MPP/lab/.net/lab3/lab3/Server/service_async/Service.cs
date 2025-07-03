using Model.app.domain;
using Services.services_async;

namespace Server.app.service_async
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

		public async Task<Child?> AddChild(string name, string cnp)
		{
			Console.WriteLine(string.Join(", ", this.loggedClients.Keys.Select(k => $"{k.Id}) {k.Username}").ToList()));

			var child = await this.ServiceChild.AddAsync(name, cnp);
			if (child != null)
			{
				NotifyChildAdded(child);
			}
			return child;
		}
		public async Task<Event?> AddEvent(string name, int minAge, int maxAge)
		{
			var event_ = await this.ServiceEvent.AddAsync(name, minAge, maxAge);
			if (event_ != null)
			{
				NotifyEventAdded(event_);
			}
			return event_;
		}
		public async Task<Signup?> AddSignup(Child child, Event event_)
		{
			var signup = await this.ServiceSignup.Add(child, event_);
			if (signup != null)
			{
				NotifySignupAdded(signup);
			}
			return signup;
		}

		public async Task<IEnumerable<Child>> GetAllChildren() =>
			await this.ServiceChild.GetAllAsync();
		public async Task<IEnumerable<Event>> GetAllEvents() =>
			await this.ServiceEvent.GetAllAsync();
		public async Task<IEnumerable<Signup>> GetAllSignups() =>
			await this.ServiceSignup.GetAll();

		public async Task<LoginInfo?>GetLoginByUsername(string username) =>
			await this.ServiceLogin.GetByUsernameAsync(username);
		public async Task<IEnumerable<Child>?> GetChildByAge(int age) =>
			await this.ServiceChild.GetByAgeAsync(age);
		public async Task<Child?> GetChildById(int id) =>
			await this.ServiceChild.GetByIdAsync(id);
		public async Task<Event?> GetEventById(int id) =>
			await this.ServiceEvent.GetByIdAsync(id);
		public async Task<IEnumerable<Signup>?> GetSignupByChild(int childId) =>
			await this.ServiceSignup.GetByChild(childId);
		public async Task<IEnumerable<Signup>?> GetSignupByEvent(int eventId) =>
			await this.ServiceSignup.GetByEvent(eventId);
		public async Task<Signup?> GetSignupByChildIdAndEventId(int childId, int eventId) =>
			await this.ServiceSignup.GetByChildIdAndEventId(childId, eventId);


		public async Task <Child?> UpdateChild(Child child) =>
			await this.ServiceChild.UpdateAsync(child);
		public async Task<Event?> UpdateEvent(Event event_) =>
			await this.ServiceEvent.UpdateAsync(event_);

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

		public async Task<IEnumerable<Event>?> GetEventByMinAge(int minAge) =>
			await this.ServiceEvent.GetByMinAgeAsync(minAge);
		public async Task<IEnumerable<Event>?> GetEventByMaxAge(int maxAge) =>
			await this.ServiceEvent.GetByMaxAgeAsync(maxAge);

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
