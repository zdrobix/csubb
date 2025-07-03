using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model.app.domain
{
	[Serializable]
	public class Child : Entity<int>
	{
		public string Name { get; set; }
		public string Cnp { get; set; }

		public Child(string Name, string Cnp)
		{
			this.Name = Name;
			this.Cnp = Cnp;
		}

		public Child() { }

		public override string ToString() =>
			$"Child{{name={Name}, cnp={Cnp}}}";

		public override bool Equals(object obj)
		{
			if (obj is Child other)
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
