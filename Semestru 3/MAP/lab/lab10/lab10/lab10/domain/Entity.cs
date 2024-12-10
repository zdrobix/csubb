using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.domain
{
	internal class Entity<ID>
	{
		private ID Id;
		public ID getId() => this.Id;
		public void setId (ID id) => this.Id = id;
	}
}
