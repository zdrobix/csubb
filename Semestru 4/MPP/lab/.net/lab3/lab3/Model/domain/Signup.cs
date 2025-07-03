using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model.app.domain
{
	[Serializable]
	public class Signup : Entity<Tuple<int, int>>
	{
		public Child Child { get; set; }
		public Event Event { get; set; }

		public Signup(Child child, Event event_)
		{
			this.Child = child;
			this.Event = event_;
		}

		public Signup() { }

		public override bool Equals(object obj)
		{
			if (obj is Signup other)
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
