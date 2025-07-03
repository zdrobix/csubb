using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ChildrenEvents;
using Grpc.Core;
using Microsoft.Extensions.Logging;
using Model.app.domain;
using Services.services;

namespace Protos
{
	public class ChildrenEventsServiceImpl : ChildrenEventsService.ChildrenEventsServiceBase, IObserver
	{
		private readonly IService ServiceImpl;
		private readonly GRPCServer GRPCService;

		public ChildrenEventsServiceImpl(IService serviceImpl, GRPCServer gRPCService)
		{
			ServiceImpl = serviceImpl;
			GRPCService = gRPCService;
		}

		public void ChildAdded(Model.app.domain.Child child)
		{
			_ = this.GRPCService.NotifyChildAdded(child);
		}

		public void EventAdded(Model.app.domain.Event event_)
		{
			_ = this.GRPCService.NotifyEventAdded(event_);
		}

		public void SignupAdded(Model.app.domain.Signup signup)
		{
			throw new NotImplementedException();
		}

		public override Task<Response> Login(Request request, ServerCallContext context)
		{
			var loginInfo = request.Entity.LoginInfo;

			try
			{
				var logged = this.ServiceImpl.Login(
					(Model.app.domain.LoginInfo)
					new Model.app.domain.LoginInfo(
						loginInfo.Username,
						loginInfo.Password
					).SetId((int)loginInfo.Id), this);
				if (logged)
				{
					var response = new Response
					{
						Type = ResponseType.Ok,
						Entity = new Entity
						{
							LoginInfo = loginInfo,
						},
						Message = "Login successful!"
					};
					return Task.FromResult(response);
				}
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = "Login failed!"
				});
			}
			catch (Exception e)
			{
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = e.Message
				});
			}
		}

		public override Task<Response> Logout(Request request, ServerCallContext context)
		{
			var loginInfo = request.Entity.LoginInfo;

			try
			{
				var loggedOut = this.ServiceImpl.Logout(
					(Model.app.domain.LoginInfo)
					new Model.app.domain.LoginInfo(
						loginInfo.Username,
						loginInfo.Password
					).SetId((int)loginInfo.Id), this);
				if (loggedOut)
				{
					var response = new Response
					{
						Type = ResponseType.Ok,
						Entity = new Entity
						{
							LoginInfo = loginInfo,
						},
						Message = "Logout successful!"
					};
					return Task.FromResult(response);
				}
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = "Logout failed!"
				});
			}
			catch (Exception e)
			{
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = e.Message
				});
			}
		}

		public override Task<Response> GetLogin(Request request, ServerCallContext context)
		{
			var username = request.Entity.LoginInfo.Username;

			try
			{
				var login = this.ServiceImpl.GetLoginByUsername(username);
				if (login != null)
				{
					var response = new Response
					{
						Type = ResponseType.LoginReceived,
						Entity = new Entity
						{
							LoginInfo = new ChildrenEvents.LoginInfo
							{
								Id = login.Id,
								Username = login.Username,
								Password = login.Password
							}
						},
						Message = "Login retrieved successfully!"
					};
					return Task.FromResult(response);
				}
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = "Login not found!"
				});
			}
			catch (Exception e)
			{
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = e.Message
				});
			}
		}

		public override Task<Response> GetChildren(Request request, ServerCallContext context)
		{
			try
			{
				var children = this.ServiceImpl.GetAllChildren();
				var response = new Response
				{
					Type = ResponseType.ChildrenReceived,
					Entities = {children.Select(c =>
							new ChildrenEvents.Entity
							{
								Child = new ChildrenEvents.Child
								{
									Id = c.Id,
									Name = c.Name,
									Cnp = c.Cnp
								}
							}
						)! },
					Message = "Children retrieved successfully!"
				};
				return Task.FromResult(response);
			}
			catch (Exception e)
			{
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = e.Message
				});
			}
		}

		public override Task<Response> GetEvents(Request request, ServerCallContext context)
		{
			try
			{
				var events = this.ServiceImpl.GetAllEvents();
				var response = new Response
				{
					Type = ResponseType.EventsReceived,
					Entities = {events.Select(e =>
							new ChildrenEvents.Entity
							{
								Event = new ChildrenEvents.Event
								{
									Id = e.Id,
									Name = e.Name,
									MinAge = e.MinAge,
									MaxAge = e.MaxAge,
								}
							}
						)! },
					Message = "Events retrieved successfully!"
				};
				return Task.FromResult(response);
			}
			catch (Exception e)
			{
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = e.Message
				});
			}
		}

		public override Task<Response> GetSignups(Request request, ServerCallContext context)
		{
			try
			{
				var signups = this.ServiceImpl.GetAllSignups();
				var response = new Response
				{
					Type = ResponseType.SignupsReceived,
					Entities = {
						signups.Select(s => new ChildrenEvents.Entity
						{
							Signup = new ChildrenEvents.Signup
							{
								Id = new ChildrenEvents.Tuple
								{
									First = s.Child.Id,
									Second = s.Event.Id
								},
								Child = new ChildrenEvents.Child
								{
									Id = s.Child.Id,
									Name = s.Child.Name,
									Cnp = s.Child.Cnp
								},
								Event = new ChildrenEvents.Event
								{
									Id = s.Event.Id,
									Name = s.Event.Name,
									MinAge = s.Event.MinAge,
									MaxAge = s.Event.MaxAge
								}
							}
						})
					},
					Message = "Signups retrieved successfully!"
				};
				return Task.FromResult(response);
			}
			catch (Exception e)
			{
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = e.Message
				});
			}
		}

		public override Task<Response> AddChild(Request request, ServerCallContext context)
		{
			try
			{
				var child = this.ServiceImpl.AddChild(request.Entity.Child.Name, request.Entity.Child.Cnp);
				var response = new Response
				{
					Type = ResponseType.ChildAdded,
					Entity = new Entity
					{
						Child = new ChildrenEvents.Child
						{
							Id = child.Id,
							Name = child.Name,
							Cnp = child.Cnp
						}
					},
					Message = "Child added successfully!"
				};
				return Task.FromResult(response);
			}
			catch (Exception e)
			{
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = e.Message
				});
			}
		}

		public override Task<Response> AddEvent(Request request, ServerCallContext context)
		{
			try
			{
				var event_ = this.ServiceImpl.AddEvent(request.Entity.Event.Name, (int)request.Entity.Event.MinAge, (int)request.Entity.Event.MaxAge);
				var response = new Response
				{
					Type = ResponseType.EventAdded,
					Entity = new Entity
					{
						Event = new ChildrenEvents.Event
						{
							Id = event_!.Id,
							Name = event_.Name,
							MinAge = event_.MinAge,
							MaxAge = event_.MaxAge
						}
					},
					Message = "Event added successfully!"
				};
				return Task.FromResult(response);
			}
			catch (Exception e)
			{
				return Task.FromResult(new Response
				{
					Type = ResponseType.Error,
					Message = e.Message
				});
			}
		}

		public override async Task Subscribe(Request request, IServerStreamWriter<ChildrenEvents.Response> responseStream, ServerCallContext context) =>
			await this.GRPCService.Subscribe(request, responseStream, context);

		public override Task<Response> Unsubscribe(Request request, IServerStreamWriter<ChildrenEvents.Response> responseStream, ServerCallContext context)
		{
			this.GRPCService.Unsubscribe(responseStream);
			var response = new Response
			{
				Type = ResponseType.Ok,
				Message = "Successfully unsubscribed."
			};
			return Task.FromResult(response);
		}
	}
}
