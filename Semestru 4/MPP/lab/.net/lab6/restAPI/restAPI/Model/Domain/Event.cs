namespace restAPI.Model.Domain
{
	public class Event : Entity<int>
	{
		public string Name { get; set; }
		public int MinAge { get; set; }
		public int MaxAge { get; set; }

		public Event(string name, int minAge, int maxAge )
		{
			Name = name;
			MinAge = minAge;
			MaxAge = maxAge;
		}

		public Event() { }
	}
}
