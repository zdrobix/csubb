using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.domain
{
	internal class Jucator : Elev
	{
		private int IdEchipa;

		public Jucator(int id, string nume, string scoala, int idEchipa) 
			: base (id, nume, scoala)
		{
			this.IdEchipa = idEchipa;
		}

		public Jucator (Elev elev, int idEchipa)
			: base(elev.getId(), elev.getName(), elev.getScoala())
		{
			this.IdEchipa = idEchipa;
		}

		public int getIdEchipaJucator() => this.IdEchipa;
	}
}
