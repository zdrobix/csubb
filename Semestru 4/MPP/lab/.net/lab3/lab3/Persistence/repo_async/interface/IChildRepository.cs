using Model.app.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistence.repo_async
{
	public interface IChildRepository
	{
		Task<Child> CreateAsync(Child child);
		Task<IEnumerable<Child>> GetAllAsync();
		Task<Child?> GetByIdAsync(int id);
		Task<Child?> UpdateAsync(Child child);
		Task<Child?> DeleteAsync(int id);
	}
}
