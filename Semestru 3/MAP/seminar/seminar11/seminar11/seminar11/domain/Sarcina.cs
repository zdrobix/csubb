using seminar11.domain.enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.domain
{
	internal class Sarcina : Entity<string>
	{
		public Dificultate DificultateSarcina { get; init; }
		public int NumarOre { get; init; }

		public Sarcina (string id, Dificultate dificultate, int numarOre)
		{
			this.DificultateSarcina = dificultate;
			this.NumarOre = numarOre;
			this.setId(id);
		}

		public Sarcina(string id, string dificultate, int numarOre)
		{
			this.DificultateSarcina = (Dificultate)Enum.Parse(typeof(Dificultate), dificultate);
			this.NumarOre = numarOre;
			this.setId(id);
		}

		public override string ToString() => $"{this.getId()} {this.DificultateSarcina} {this.NumarOre}";
	}
}
