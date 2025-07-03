using Model.app.domain;
using Client.app.password;
using System.Configuration;
using Services.services;
using System.Diagnostics;

namespace Client.app.controler
{
	public class Controller : IObserver
	{
		private IService Service;
		private LoginInfo? CurrentUser;

		public event EventHandler<UpdateEventArgs>? UpdateEventHandler;

		public Controller(IService service) => this.Service = service; 

		public LoginInfo? CanLogin (string username, string password)
		{
			var login = this.Service.GetLoginByUsername(username);
			Debug.WriteLine($"login: {login.Username}, {login.Password} | {username}, {password}");
			if (login == null)
				return null;
			if (login.Password == Crypter.Encrypt(password, ConfigurationManager.AppSettings["EncryptionKey"]!))
				return login;
			return null;
		}
		public bool Login (LoginInfo login)
		{
			if (login == null)
				return false;
			this.CurrentUser = login;
			return this.Service.Login(login, this);
		}
		public void Logout()
		{
			if (this.CurrentUser == null)
				return;
			this.Service.Logout(this.CurrentUser, this);
			this.CurrentUser = null;
		}

		public IEnumerable<Child> GetAllChildren() => this.Service.GetAllChildren();
		public Child GetChildById (int id)
		{
			var child = this.Service.GetChildById(id);
			if (child == null)
				throw new ArgumentException("Invalid id.");
			return child;
		}
		public Child? UpdateChild (int id, string name, string cnp)
		{
			if (Service.GetChildById(id) == null)
				throw new ArgumentException("Invalid id.");
			ValidateChild(name, cnp);
			return this.Service.UpdateChild((Child)new Child(name, cnp).SetId(id));
		}
		public IEnumerable<Child>? GetChildrenByAge(int age) => this.Service.GetChildByAge(age);
		public void AddChild(string name, string cnp)
		{
			ValidateChild(name, cnp);
			this.Service.AddChild(name, cnp);
		}

		
		public IEnumerable<Event> GetAllEvents() => this.Service.GetAllEvents();
		public Event GetEventById(int id)
		{
			var event_ = this.Service.GetEventById(id);
			if (event_ == null)
				throw new ArgumentException("Invalid id.");
			return event_;
		}
		public Event UpdateEvent (int id, string name, int minAge, int maxAge)
		{
			if (Service.GetEventById(id) == null)
				throw new ArgumentException("Invalid id.");
			ValidateEvent(name, minAge, maxAge);
			return this.Service.UpdateEvent((Event)new Event(name, minAge, maxAge).SetId(id))!;
		}
		public IEnumerable<Event>? GetEventsByMinAge(int minAge) => this.Service.GetEventByMinAge(minAge);
		public IEnumerable<Event>? GetEventsByMaxAge(int maxAge) => this.Service.GetEventByMaxAge(maxAge);
		public void AddEvent(string name, int minAge, int maxAge)
		{
			ValidateEvent(name, minAge, maxAge);
			this.Service.AddEvent(name, minAge, maxAge);
		}


		public IEnumerable<Signup> GetAllSignups() => this.Service.GetAllSignups();
		public IEnumerable<Signup>? GetAllSignupsByChildId(int id) => this.Service.GetSignupByChild(id);
		public IEnumerable<Signup>? GetAllSignupsByEventId(int id) => this.Service.GetSignupByEvent(id);

		public void AddSignup(int childId, int eventId)
		{
			// N am avut chef sa scriu functiile de get si pentru proxy.
			// Schimba mai tarziu.
			//var child = this.Service.GetChildById(childId);
			//var event_ = this.Service.GetEventById(eventId);
			var child = this.Service.GetAllChildren().FirstOrDefault(c => c.Id == childId);
			var event_ = this.Service.GetAllEvents().FirstOrDefault(e => e.Id == eventId);
			if (child == null)
				throw new ArgumentException("Invalid child id.");
			if (event_ == null)
				throw new ArgumentException("Invalid event id.");
			this.Service.AddSignup(child, event_);
		}


		public static void ValidateChild (string name, string cnp)
		{
			if (name.Count() < 3)
				throw new ArgumentException("Name too short.");
			if (cnp.Count() < 6)
				throw new ArgumentException("CNP too short.");
		}

		public static void ValidateEvent (string name, int minAge, int maxAge)
		{
			if (name.Count() < 3)
				throw new ArgumentException("Name too short.");
			if (minAge > maxAge || minAge < 0)
				throw new ArgumentException("Invalid minimum age.");
		}

		public void ChildAdded(Child child) =>
			this.UpdateEventHandler?.Invoke(this, new UpdateEventArgs(UpdateType.ChildAdded, child));

		public void EventAdded(Event event_) =>
			this.UpdateEventHandler?.Invoke(this, new UpdateEventArgs(UpdateType.EventAdded, event_));

		public void SignupAdded(Signup signup) =>
			this.UpdateEventHandler?.Invoke(this, new UpdateEventArgs(UpdateType.SignupAdded, signup));
	}
}
