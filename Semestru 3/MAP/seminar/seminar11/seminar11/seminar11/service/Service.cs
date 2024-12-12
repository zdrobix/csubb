using seminar11.domain;
using seminar11.repo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.service
{
	internal class Service
	{
		private InFileRepository<Angajat, int> RepoAngajat;
		private InFileRepository<Sarcina, string> RepoSarcina;
		private InFileRepository<Pontaj, Tuple<int, string>> RepoPontaj;

		public Service ()
		{
			this.RepoSarcina = new InFileRepository<Sarcina, string>();
			this.RepoAngajat = new InFileRepository<Angajat, int>();
			this.RepoPontaj = new InFileRepository<Pontaj, Tuple<int, string>>();

			this.RepoSarcina.LoadFromFile();
			this.RepoAngajat.LoadFromFile();
			this.RepoPontaj.LoadFromFile();
		}

		public IEnumerable<Angajat>? GetAngajati() => this.RepoAngajat.FindAll();
		public IEnumerable<Pontaj>? GetPontaje() => this.RepoPontaj.FindAll();
		public IEnumerable<Sarcina>? GetSarcini() => this.RepoSarcina.FindAll();

		public Angajat? GetAngajat(int id) => this.RepoAngajat!.FindAll()!.First(x=> x.getId().Equals(id));
		public Sarcina? GetSarcina(string id) => this.RepoSarcina!.FindAll()!.First(x => x.getId().Equals(id));
	}
}
