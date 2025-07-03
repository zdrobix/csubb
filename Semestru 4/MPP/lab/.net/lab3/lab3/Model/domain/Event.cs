using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model.app.domain
{
	[Serializable]
	public class Event : Entity<int>
	{
		public string Name { get; set; }
		public int MinAge { get; set; }
		public int MaxAge { get; set; }
		public string Interval => $"{MinAge} - {MaxAge}";


		public Event(string name, int minAge, int maxAge)
		{
			this.Name = name;
			this.MinAge = minAge;
			this.MaxAge = maxAge;
		}

		public Event() { }

		public override string ToString() =>
						$"Event{{name={Name}, minAge={MinAge}, maxAge={MaxAge}}}";

		public override bool Equals(object obj)
		{
			if (obj is Event other)
			{
				return Id == other.Id;
			}
			return false;
		}

		public override int GetHashCode()
		{
			return Id.GetHashCode();
		}
	}
}
