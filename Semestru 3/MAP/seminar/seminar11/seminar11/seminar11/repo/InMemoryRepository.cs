using seminar11.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.repo
{
	internal class InMemoryRepository<E, ID> where E : Entity<ID>
	{
		protected Dictionary<ID, E> Entities { get; set; }
		public IEnumerable<E>? FindAll() => (IEnumerable<E>)this.Entities.Values.AsEnumerable();
	}
}
