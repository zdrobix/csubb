using Model.app.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistence.repo_async
{
	public interface ISignupRepository
	{
		Task<Signup> CreateAsync(Signup signup);
		Task<IEnumerable<Signup>> GetAllAsync();
		Task<IEnumerable<Signup>?> GetByChildId(int id);
		Task<IEnumerable<Signup>?> GetByEventId(int id);
		Task<Signup?> GetByChildIdAndEventId(int childId, int eventId);
		Task<Signup?> UpdateAsync(Signup signup);
		Task<Signup?> DeleteAsync(int childId, int eventId);
	}
}
