using seminar11.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.repo
{
	internal class InFileRepository<E, ID> : InMemoryRepository<E, ID> where E : Entity<ID>
	{
		private string? FileName;
		public InFileRepository () => this.FileName = TypeMatching<E, ID>.GetFile();

		public void LoadFromFile ()
		{
			base.Entities = new Dictionary<ID, E>();
			using (var reader = new StreamReader(this.FileName))
			{
				var line = reader.ReadLine();
				while (line != null)
				{
					var entity = TypeMatching<E, ID>.CreateEntityFromList(
						line.Split(',').ToList()
					);
					base.Entities.Add(entity!.getId()!, entity);
					line = reader.ReadLine();
				}
			}
		}
	}
}
