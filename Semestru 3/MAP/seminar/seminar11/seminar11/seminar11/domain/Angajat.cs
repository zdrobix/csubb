using seminar11.domain.enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.domain
{
	internal class Angajat : Entity<int>
	{
		public string Nume { get; init; }
		public double VenitPeOra { get; init; }
		public Nivel NivelAngajat { get; init; }

		public Angajat(int id, string nume, double venit, Nivel nivel)
		{
			Nume = nume;
			VenitPeOra = venit;
			NivelAngajat = nivel;
			this.setId(id);
		}

		public Angajat(int id, string nume, double venit, string nivel)
		{
			Nume = nume;
			VenitPeOra = venit;
			NivelAngajat = (Nivel)Enum.Parse(typeof(Nivel), nivel);
			this.setId(id);
		}

		public override string ToString() => $"{Nume} {VenitPeOra} {NivelAngajat}";

	}
}
