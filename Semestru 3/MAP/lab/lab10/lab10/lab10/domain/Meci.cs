using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.domain
{
	internal class Meci : Entity<int>
	{
		private int EchipaGazde;
		private int EchipaOaspeti;
		private DateOnly Date;

		public Meci(int id, int echipaGazde, int echipaOaspecti, DateOnly date)
		{
			this.EchipaGazde = echipaGazde;
			this.EchipaOaspeti = echipaOaspecti;
			this.Date = date;
			this.setId(id);
		}
		public int getIdGazde() => this.EchipaGazde;
		public int getIdOaspeti() => this.EchipaOaspeti;
		public DateOnly getDate() => this.Date;	
	}
}
