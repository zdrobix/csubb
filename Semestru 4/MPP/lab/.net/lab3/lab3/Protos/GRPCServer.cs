using Services.services;
using Grpc.Core;
using Networking.utils;
using Networking.rpcprotocol;
using System.Collections.Concurrent;
using ChildrenEvents;
using System;

namespace Protos
{
	public class GRPCServer 
	{
		private Grpc.Core.Server Server;
		private readonly string Host;
		private readonly int Port;
		private readonly IService ServiceImpl;

		public GRPCServer(string host, int port, IService service)
		{
			this.Host = host;
			this.Port = port;
			this.ServiceImpl = service;
		}

		public Task Start(CancellationToken cancellationToken)
		{
			this.Server = new Grpc.Core.Server
			{
				Services = { ChildrenEventsService.BindService(new ChildrenEventsServiceImpl(this.ServiceImpl, this)) },
				Ports = { new ServerPort(Host, Port, ServerCredentials.Insecure) } 
			};
			this.Server.Start();

			return Task.CompletedTask;
		}

		public Task Stop(CancellationToken cancellationToken)
		{
			this.Server.ShutdownAsync();

			return Task.CompletedTask;
		}

		public async Task Subscribe(Request request, IServerStreamWriter<ChildrenEvents.Response> responseStream, ServerCallContext context)
		{
			ObserverManager.AddObserver((int)request.Entity.LoginInfo.Id, responseStream);
			try
			{
				while(!context.CancellationToken.IsCancellationRequested)
					await Task.Delay(1000);
			}
			catch (TaskCanceledException)
			{
			}
			ObserverManager.RemoveObserver(responseStream);
		}

		public void Unsubscribe(IServerStreamWriter<Response> responseStream) =>
			ObserverManager.RemoveObserver(responseStream);
		public void UnsubscribeAll() =>
			ObserverManager.Clear();

		public async Task NotifyChildAdded(Model.app.domain.Child child)
		{
			var response = new ChildrenEvents.Response
			{
				Type = ChildrenEvents.ResponseType.ChildAdded,
				Entity = new Entity
				{
					Child = new Child
					{
						Id = child.Id,
						Name = child.Name,
						Cnp = child.Cnp,
					}
				}
			};

			var clientsCopy = ObserverManager.GetObservers();
			foreach (var (id, client) in clientsCopy)
			{
				try
				{
					await client.WriteAsync(response);
				}
				catch (Exception ex)
				{
					Console.WriteLine($"Failed to send update: {ex.Message}");
					ObserverManager.RemoveObserver(client);
				}
			}
		}

		public async Task NotifyEventAdded(Model.app.domain.Event event_)
		{
			var response = new ChildrenEvents.Response
			{
				Type = ChildrenEvents.ResponseType.EventAdded,
				Entity = new Entity
				{
					Event = new ChildrenEvents.Event
					{
						Id = event_!.Id,
						Name = event_.Name,
						MinAge = event_.MinAge,
						MaxAge = event_.MaxAge
					}
				}
			};

			var clientsCopy = ObserverManager.GetObservers();
			foreach (var (id, client) in clientsCopy)
			{
				try
				{
					await client.WriteAsync(response);
				}
				catch (Exception ex)
				{
					Console.WriteLine($"Failed to send update: {ex.Message}");
					ObserverManager.RemoveObserver(client);
				}
			}
		}
	}
}
