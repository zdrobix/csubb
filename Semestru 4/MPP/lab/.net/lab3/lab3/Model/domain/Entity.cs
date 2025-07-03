using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model.app.domain
{
	[Serializable]
	public class Entity<ID>
	{
		public ID Id { get; set; }

		public Entity()
		{

		}

		public Entity<ID> SetId(ID id)
		{
			this.Id = id;
			return this;
		}
	}
}
