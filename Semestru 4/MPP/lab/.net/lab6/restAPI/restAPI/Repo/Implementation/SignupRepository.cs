using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using restAPI.Data;
using restAPI.Model.Domain;
using restAPI.Repo.Interface;

namespace restAPI.Repo.Implementation
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
			await DbContext.Signups.AddAsync(signup);
			await DbContext.SaveChangesAsync();
			return signup;
		}

		public async Task<Signup?> DeleteAsync(int childId, int eventId)
		{
			var existingSignup = await DbContext.Signups.FirstOrDefaultAsync(e => e.ChildId == childId && e.EventId == eventId);
			if (existingSignup == null)
			{
				return null;
			}
			DbContext.Signups.Remove(existingSignup);
			await DbContext.SaveChangesAsync();
			return existingSignup;
		}

		public async Task<IEnumerable<Signup>> GetAllAsync() =>
			await DbContext.Signups.ToListAsync();

		public async Task<Signup?> GetByIdAsync(int childId, int eventId) =>
			await DbContext.Signups.FirstOrDefaultAsync(e => e.ChildId == childId && e.EventId == eventId);

		public async Task<Signup?> UpdateAsync(Signup signup)
		{
			var existingSignup = await DbContext.Signups.FirstOrDefaultAsync(e => e.ChildId == signup.ChildId && e.EventId == signup.EventId);
			if (existingSignup == null)
			{
				return null;
			}
			DbContext.Entry(existingSignup).CurrentValues.SetValues(signup);
			await DbContext.SaveChangesAsync();
			return existingSignup;
		}
	}
}
