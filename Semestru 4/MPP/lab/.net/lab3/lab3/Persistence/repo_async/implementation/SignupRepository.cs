using Model.app.domain;
using Persistence.data;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistence.repo_async.implementation
{
	public class SignupRepository : ISignupRepository
	{
		private readonly AppDbContext DbContext;

		public SignupRepository(AppDbContext dbContext)
		{
			DbContext = dbContext;
		}

		public async Task<Signup> CreateAsync(Signup signup)
		{
			await DbContext.AddAsync(signup);
			await DbContext.SaveChangesAsync();
			return signup;
		}

		public async Task<IEnumerable<Signup>> GetAllAsync() =>
			await DbContext.Signups
			.Include(s => s.Child)
			.Include(s => s.Event)
			.ToListAsync();

		public async Task<IEnumerable<Signup>?> GetByChildId(int id) =>
			await DbContext.Signups
			.Include(s => s.Child)
			.Include(s => s.Event)
			.Where(s => s.Child.Id == id)
			.ToListAsync();

		public async Task<IEnumerable<Signup>?> GetByEventId(int id) =>
			await DbContext.Signups
			.Include(s => s.Child)
			.Include(s => s.Event)
			.Where(s => s.Event.Id == id)
			.ToListAsync();

		public async Task<Signup?> GetByChildIdAndEventId(int childId, int eventId) =>
			await DbContext.Signups
			.Include(s => s.Child)
			.Include(s => s.Event)
			.FirstOrDefaultAsync(s => s.Child.Id == childId && s.Event.Id == eventId);

		public async Task<Signup?> UpdateAsync(Signup signup)
		{
			throw new NotImplementedException();
		}

		public async Task<Signup?> DeleteAsync(int childId, int eventId)
		{
			var existingSignup = await DbContext.Signups.FirstOrDefaultAsync(s => s.Child.Id == childId && s.Event.Id == eventId);
			if (existingSignup == null)
				throw new EntryPointNotFoundException("Signup not found");
			DbContext.Signups.Remove(existingSignup);
			await DbContext.SaveChangesAsync();
			return existingSignup;
		}
	}
}
