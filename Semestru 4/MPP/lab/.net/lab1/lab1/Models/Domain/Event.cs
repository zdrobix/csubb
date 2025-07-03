using System.Reflection;

namespace lab1.Models.Domain
{
	public class Event : Entity<int>
	{
        public string Name { get; set; }
        public int MinAge { get; set; }
        public int MaxAge { get; set; }

        public Event(string name, int minAge, int maxAge)
        {
            this.Name = name;
            this.MaxAge = minAge;
            this.MaxAge = maxAge;
		}

        public Event() { }
    }
}
