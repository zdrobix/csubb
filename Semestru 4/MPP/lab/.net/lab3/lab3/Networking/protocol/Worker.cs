using Model.app.domain;
using Networking.rpcprotocol;
using Services.services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Networking.protocol
{
	public class Worker : IObserver
	{
		private TcpClient Client;
		private IService Service;

		private NetworkStream Stream;
		private IFormatter Formatter;
		private volatile bool Connected;

		public Worker(IService service, TcpClient client)
		{
			this.Client = client;
			this.Service = service;
			try
			{
				this.Stream = client.GetStream();
				Formatter = new System.Runtime.Serialization.Formatters.Binary.BinaryFormatter();
				Connected = true;
			} catch (Exception e)
			{
				Console.WriteLine("Error: " + e.Message);
			}
		}

		public void ChildAdded(Child child)
		{
			Response<int> response = new Response<int>() { Type = ResponseType.CHILD_ADDED, Entity = child };
			this.SendResponse(response);
		}

		public void EventAdded(Event event_)
		{
			Response<int> response = new Response<int>() { Type = ResponseType.EVENT_ADDED, Entity = event_ };
			this.SendResponse(response);
		}

		public void SignupAdded(Signup signup)
		{
			Response<int> response = new Response<int>() { Type = ResponseType.SIGNUP_ADDED, Entities = new List<Entity<int>> { signup.Child, signup.Event } };
			this.SendResponse(response);
		}

		public Response<int> HandleRequest(Request<int> request)
		{
			Response<int> response = null;
			switch(request.Type)
			{
				case RequestType.LOGIN:
				{
					LoginInfo login = (LoginInfo)request.Entity!;
					lock(this.Service)
					{
						if (this.Service.Login(login, this))
							response = new Response<int>() { Type = ResponseType.OK, Entity = login };
						else response = new Response<int>() { Type = ResponseType.ERROR, ErrorMessage = "Error!" };
					}
					break;
				}
				case RequestType.GET_LOGIN:
				{
					LoginInfo? login;
					lock (this.Service) {
						login = this.Service.GetLoginByUsername(request.Message!);
					}
					if (login == null)
						response = new Response<int>() { Type = ResponseType.ERROR, ErrorMessage = "Login not found!" };
					else response = new Response<int>() { Type = ResponseType.LOGIN_RECEIVED, Entity = login };
					break;
				}
				case RequestType.LOGOUT:
				{
					LoginInfo login = (LoginInfo)request.Entity!;
					lock (this.Service)
					{
						this.Service.Logout(login, this);
					}
					this.Connected = false;
					response = new Response<int>() { Type = ResponseType.OK };
					break;
				}
				case RequestType.ADD_CHILD:
				{
					Child child = (Child)request.Entity!;
					lock (this.Service)
					{
						response = new Response<int>() { Type = ResponseType.CHILD_ADDED, Entity = this.Service.AddChild(child.Name, child.Cnp) };
					}
					break;
				}
				case RequestType.ADD_EVENT:
				{
					Event event_ = (Event)request.Entity!;
					lock (this.Service)
					{
						response = new Response<int>() { Type = ResponseType.EVENT_ADDED, Entity = this.Service.AddEvent(event_.Name, event_.MinAge, event_.MaxAge) };
					}
					break;
				}
				case RequestType.ADD_SIGNUP:
				{
					Signup? signup;
					lock (this.Service)
					{
						signup = this.Service.AddSignup((Child)request.Entities!.ElementAt(0), (Event)request.Entities!.ElementAt(1))!;
					}
					if (signup == null)
						response = new Response<int>() { Type = ResponseType.ERROR, ErrorMessage = "Error!" };
					else response = new Response<int>() { Type = ResponseType.SIGNUP_ADDED, Entities = new List<Entity<int>> {signup.Child, signup.Event}};
					break;
				}
				case RequestType.GET_CHILDREN:
				{
					lock (this.Service)
					{
						response = new Response<int>() { Type = ResponseType.CHILDREN_RECEIVED, Entities = this.Service.GetAllChildren() };
					}
					break;
				}
				case RequestType.GET_EVENTS:
				{
					lock (this.Service)
					{
						response = new Response<int>() { Type = ResponseType.EVENTS_RECEIVED, Entities = this.Service.GetAllEvents() };
					}
					break;
				}
				case RequestType.GET_SIGNUPS:
				{
					lock (this.Service)
					{
						response = new Response<int>()
						{
							Type = ResponseType.SIGNUPS_RECEIVED,
							Entities = this.Service.GetAllSignups()
											.SelectMany(signup => new Entity<int>[] { signup.Child, signup.Event }).ToList()
						};
					}
					break;
				}
			}
			return response!;
		}

		private void SendResponse(Response<int> response)
		{
			Console.WriteLine($"Sending response {response}");
			lock (Stream)
			{
				Formatter.Serialize(Stream, response);
				Stream.Flush();
			}
		}

		public virtual void run()
		{
			while(this.Connected)
			{
				try
				{
					Request<int> request = (Request<int>)Formatter.Deserialize(Stream);
					Response<int> response = HandleRequest(request);
					if (response != null)
						this.SendResponse(response);
				} catch (Exception e)
				{
					Console.WriteLine("Error: " + e.Message);
					this.CloseConnection();
					break;
				}
			}
			this.CloseConnection();
		}

		private void CloseConnection()
		{
			this.Connected = false;
			Stream.Close();
			Client.Close();
		}
	}
}
