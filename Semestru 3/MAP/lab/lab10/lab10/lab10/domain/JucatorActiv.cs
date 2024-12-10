using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace lab10.domain
{
	internal class JucatorActiv : Entity<Tuple<int, int>>
	{
		private int PuncteInscrise;
		private Tip TipJucator;

		public JucatorActiv (int idJucator, int idMeci, int puncteInscrise, Tip tipJucator)
		{
			this.PuncteInscrise = puncteInscrise;
			this.TipJucator = tipJucator;
			this.setId(Tuple.Create(idJucator, idMeci));
		}

		public int getIdJucator() => this.getId().Item1;
		public int getIdMeci() => this.getId().Item2;
		public int getPuncteInscrise() => this.PuncteInscrise;
		public Tip getTipJucator() => this.TipJucator;
	}
}
