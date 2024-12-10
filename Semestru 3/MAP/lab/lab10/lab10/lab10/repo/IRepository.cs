using lab10.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.repo
{
	internal interface IRepository<E, ID> where E : Entity<ID>
	{
		public E? Find(ID id);
		public E? Save(E Entity);
		public void Delete(ID id);
		public IEnumerable<E> FindAll();
		public E? Update (E Entity);
	}
}
