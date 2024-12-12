using seminar11.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.repo
{
	internal interface IRepository<E, ID> where E : Entity<ID>
	{
		public IEnumerable<E> FindAll();
	}
}
