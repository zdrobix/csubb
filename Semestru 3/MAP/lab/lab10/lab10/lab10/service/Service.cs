using lab10.domain;
using lab10.repo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace lab10.service
{
	internal class Service
	{
		private Repository<Elev, int> RepoElevi;
		private Repository<Jucator, int> RepoJucator;
		private Repository<JucatorActiv, Tuple<int, int>> RepoJucatorActiv;
		private Repository<Echipa, int> RepoEchipe;
		private Repository<Meci, int> RepoMeci;

		public Service ()
		{
			this.RepoEchipe = new Repository<Echipa, int> ();
			this.RepoElevi = new Repository<Elev, int> ();
			this.RepoJucator = new Repository<Jucator, int> ();
			this.RepoJucatorActiv = new Repository<JucatorActiv, Tuple<int, int>>();
			this.RepoMeci = new Repository<Meci, int> ();
		}

		public IEnumerable<Echipa> GetEchipe() => this.RepoEchipe.FindAll();
		public IEnumerable<Jucator> GetJucatori() => this.RepoJucator.FindAll()
				.Select(j => new Jucator(this.RepoElevi.Find(j.getId())!, j.getIdEchipaJucator()))
				.Where(j => j != null);
		public IEnumerable<Meci> GetMeciuri() => this.RepoMeci.FindAll();
		public IEnumerable<JucatorActiv> GetJucatoriActivi() => this.RepoJucatorActiv.FindAll();
		public Echipa? GetEchipa(int id) => this.RepoEchipe.Find(id);
		public Elev? GetElev(int id) => this.RepoElevi.Find(id);
		public Jucator? GetJucator(int id) => this.RepoJucator.Find(id);
		public Meci? GetMeci(int id) => this.RepoMeci.Find(id);
	}
}
