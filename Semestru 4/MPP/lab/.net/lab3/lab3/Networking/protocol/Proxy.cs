using Model.app.domain;
using Networking.rpcprotocol;
using Services.services;
using System.IO;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;

namespace Networking.protocol
{
	public class Proxy : IService
	{
		private string Host;
		private int Port;

		private IObserver Client;

		private NetworkStream Stream;
		private IFormatter Formatter;
		private TcpClient Connection;

		private Queue<Response<int>> Responses;

		private volatile bool Finished;
		private EventWaitHandle WaitHandle;


		public Proxy(string host, int port)
		{
			this.Host = host;
			this.Port = port;
			Responses = new Queue<Response<int>>();
			initializeConnection();
		}

		public Child? AddChild(string name, string cnp)
		{
			var request = new Request<int> { Type = RequestType.ADD_CHILD, Entity = new Child(name, cnp) };
			this.sendRequest(request);
			return null;
		}

		public Event? AddEvent(string name, int minAge, int maxAge)
		{
			var request = new Request<int> { Type = RequestType.ADD_EVENT, Entity = new Event(name, minAge, maxAge) };
			this.sendRequest(request);
			return null;
		}

		public Signup? AddSignup(Child child, Event event_)
		{
			var request = new Request<int> { Type = RequestType.ADD_SIGNUP, Entities = new List<Entity<int>> { child, event_ } };
			this.sendRequest(request);
			return null;
		}

		public IEnumerable<Child> GetAllChildren()
		{
			var request = new Request<int> { Type = RequestType.GET_CHILDREN };
			this.sendRequest(request);
			var response = readResponse();

			if (response!.Type == ResponseType.CHILDREN_RECEIVED)
				return response.Entities!.Select(e => (Child)e).ToList();
			
			Console.WriteLine("Error receiving children.");
			return null!;
		}

		public IEnumerable<Event> GetAllEvents()
		{
			var request = new Request<int> { Type = RequestType.GET_EVENTS };
			this.sendRequest(request);
			var response = readResponse();

			if (response!.Type == ResponseType.EVENTS_RECEIVED)
				return response.Entities!.Select(e => (Event)e).ToList();

			Console.WriteLine("Error receiving events.");
			return null!; ;
		}

		public IEnumerable<Signup> GetAllSignups()
		{
			var request = new Request<int> { Type = RequestType.GET_SIGNUPS };
			this.sendRequest(request);
			var response = readResponse();
			var signups = new List<Signup>();
			if (response!.Type == ResponseType.SIGNUPS_RECEIVED)
			{
				for (int i = 0; i < response.Entities!.Count();)
				{
					var signup = new Signup(
						(Child)response.Entities!.ElementAt(i++),
						(Event)response.Entities!.ElementAt(i++)
					);
					signups.Add(
						(Signup)signup.SetId(Tuple.Create(signup.Child.Id, signup.Event.Id))
					);
				}
				return signups;
			}
			Console.WriteLine("Error receiving signups.");
			return null!;
		}

		public LoginInfo GetLoginByUsername(string username)
		{
			var request = new Request<int> { Type = RequestType.GET_LOGIN, Message = username };
			this.sendRequest(request);
			var response = readResponse();
			if (response!.Type == ResponseType.LOGIN_RECEIVED)
				return (LoginInfo)response.Entity!;

			return null!;
		}

		public IEnumerable<Child>? GetChildByAge(int age)
		{
			return null;
		}

		public Child? GetChildById(int id)
		{
			return null;
		}

		public Event? GetEventById(int id)
		{
			return null;
		}

		public IEnumerable<Signup>? GetSignupByChild(int childId)
		{
			return null;
		}

		public IEnumerable<Signup>? GetSignupByEvent(int eventId)
		{
			return null;
		}

		public Signup? GetSignupById(int childId, int eventId)
		{
			return null;
		}

		public bool Login(LoginInfo login, IObserver client)
		{
			try
			{
				var request = new Request<int> { Type = RequestType.LOGIN, Entity = login  };
				this.sendRequest(request);
				Response<int> response = readResponse()!;
				if (response.Type == ResponseType.OK)
				{
					this.Client = client;
					return true;
				}
				Console.WriteLine("Login failed: " + response.ErrorMessage);
			} catch (Exception e)
			{
				Console.WriteLine("Login error: " + e.Message);
			}
			return false;
		}

		public bool Logout(LoginInfo login, IObserver client)
		{
			var request = new Request<int> { Type = RequestType.LOGOUT, Entity = login };
			this.sendRequest(request);
			Response<int> response = readResponse()!;
			if (response.Type == ResponseType.OK)
			{
				this.Client = null!;
				this.closeConnection();
				return true;
			}
			return false;
		}

		public Child? UpdateChild(Child child)
		{
			return null;
		}

		public Event? UpdateEvent(Event event_)
		{
			return null;
		}

		private void closeConnection()
		{
			Finished = true;
			try
			{
				Stream.Close();

				Connection.Close();
				WaitHandle.Close();
				Client = null!;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
			}

		}

		private void sendRequest(Request<int> request)
		{
			Console.WriteLine("Sending request: " + request);
			try
			{
				lock (Stream)
				{
					Formatter.Serialize(Stream, request);
					Stream.Flush();
				}
			}
			catch (Exception e)
			{
				throw new Exception($"Error sending request: {request}\n" + e.Message, e);
			}

		}

		private Response<int>? readResponse()
		{
			Response<int> response = null;
			try
			{
				WaitHandle.WaitOne();
				lock (Responses)
				{
					//Monitor.Wait(responses); 
					response = Responses.Dequeue();
				}

			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
			}
			return response;
		}
		private void initializeConnection()
		{
			try
			{
				Connection = new TcpClient(Host, Port);
				Stream = Connection.GetStream();
				Formatter = new BinaryFormatter();
				Finished = false;
				WaitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
			}
		}
		private void startReader()
		{
			Thread tw = new Thread(run);
			tw.Start();
		}


		private void handleUpdate(Response<int> response)
		{
			switch (response.Type)
			{
				case ResponseType.CHILD_ADDED:
					Client.ChildAdded((Child)response.Entity!);
					break;
				case ResponseType.EVENT_ADDED:
					Client.EventAdded((Event)response.Entity!);
					break;
				case ResponseType.SIGNUP_ADDED:
					var signup = new Signup(
						(Child)response.Entities!.ElementAt(0),
						(Event)response.Entities!.ElementAt(1)
					);
					Client.SignupAdded(
						(Signup)signup.SetId(Tuple.Create(signup.Child.Id, signup.Event.Id))
					);
					break;
			}
		}
		public virtual void run()
		{
			while (!this.Finished)
			{
				try
				{
					Response<int> response = (Response<int>)Formatter.Deserialize(Stream);
					Console.WriteLine("response received " + response);
					if (response.Type == ResponseType.CHILD_ADDED ||
						response.Type == ResponseType.EVENT_ADDED ||
						response.Type == ResponseType.SIGNUP_ADDED)
					{
						handleUpdate(response);
						continue;
					}

					lock (Responses)
					{
						Responses.Enqueue(response);
					}
					WaitHandle.Set();
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error " + e);
				}

			}
		}

		public IEnumerable<Event>? GetEventByMinAge(int minAge)
		{
			return null;
		}

		public IEnumerable<Event>? GetEventByMaxAge(int maxAge)
		{
			return null;
		}
	}
}
