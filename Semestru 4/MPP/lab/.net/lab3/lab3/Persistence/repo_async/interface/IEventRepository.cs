using Model.app.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistence.repo_async
{
	public interface IEventRepository
	{
		Task<Event> CreateAsync(Event event_);
		Task<IEnumerable<Event>> GetAllAsync();
		Task<Event?> GetByIdAsync(int id);
		Task<Event?> UpdateAsync(Event child);
		Task<Event?> DeleteAsync(int id);
	}
}
