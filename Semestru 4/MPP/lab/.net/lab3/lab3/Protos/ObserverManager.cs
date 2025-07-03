using Grpc.Core;

namespace Protos
{
	public static class ObserverManager
	{
		private static readonly Dictionary<int, IServerStreamWriter<ChildrenEvents.Response>> Observers =
			new Dictionary<int, IServerStreamWriter<ChildrenEvents.Response>>();
		private static readonly object Lock = new();

		public static void AddObserver(int id, IServerStreamWriter<ChildrenEvents.Response> observer)
		{
			lock(Lock)
			{
				Observers[id] = observer;
			}
		}

		public static void RemoveObserver(IServerStreamWriter<ChildrenEvents.Response> observer)
		{
			lock (Lock)
			{
				var index = Observers.FirstOrDefault(k => k.Value == observer).Key;
				if (index != 0)
				{
					Observers.Remove(index);
				}
			}
		}

		public static IEnumerable<KeyValuePair<int, IServerStreamWriter<ChildrenEvents.Response>>> GetObservers()
		{
			lock (Lock)
			{
				return Observers.ToList(); 
			}
		}

		public static int Count()
		{
			lock (Lock)
			{
				return Observers.Count;
			}
		}

		public static void Clear()
		{
			lock (Lock)
			{
				Observers.Clear();
			}
		}
	}
}
