using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.domain
{
	internal class Pontaj : Entity<Tuple<int, string>>
	{
		public DateTime DataPontaj { get; init; }

		public Pontaj (Tuple<int, string> id, DateTime dataPontaj)
		{
			this.DataPontaj = dataPontaj;
			this.setId(id);
		}

		public Pontaj(Tuple<int, string> id, string dataPontaj)
		{
			this.DataPontaj = DateTime.Parse(dataPontaj);
			this.setId(id);
		}

		public override string ToString() => $"{this.getId().Item1} {this.getId().Item2} {this.DataPontaj}";
	}
}
