using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.domain
{
	internal class Elev : Entity<int>
	{
		private string Nume;
		private string Scoala;

		public Elev (int id, string nume, string scoala)
		{
			this.Nume = nume;
			this.Scoala = scoala;
			this.setId(id);
		}
		public string getName() => this.Nume;
		public string getScoala() => this.Scoala;
	}
}
