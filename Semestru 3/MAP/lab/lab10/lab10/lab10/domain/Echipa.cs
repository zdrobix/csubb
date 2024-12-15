using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.domain
{
	internal class Echipa : Entity<int>
	{
		private string Nume;

		public Echipa(int id, string nume)
		{
			this.setId(id);
			this.Nume = nume;
		}
		public string getName() => this.Nume;

		public override string ToString() => this.Nume;
	}
}
