using Microsoft.EntityFrameworkCore;
using restAPI.Data;
using restAPI.Model.Domain;
using restAPI.Repo.Interface;

namespace restAPI.Repo.Implementation
{
	public class ChildRepository : IChildRepository
	{
		private readonly AppDbContext DbContext;

		public ChildRepository(AppDbContext dbContext)
		{
			DbContext = dbContext;
		}

		public async Task<Child> CreateAsync(Child child)
		{
			await DbContext.Children.AddAsync(child);
			await DbContext.SaveChangesAsync();
			return child;
		}

		public async Task<Child?> DeleteAsync(int id)
		{
			var existingChild = await DbContext.Children.FirstOrDefaultAsync(e => e.Id == id);
			if (existingChild == null)
			{
				return null;
			}
			DbContext.Children.Remove(existingChild);
			await DbContext.SaveChangesAsync();
			return existingChild;
		}

		public async Task<IEnumerable<Child>> GetAllAsync() =>
			await DbContext.Children.ToListAsync();

		public async Task<Child?> GetByIdAsync(int id) =>
			await DbContext.Children.FirstOrDefaultAsync(e => e.Id == id);

		public async Task<Child?> UpdateAsync(Child child)
		{
			var existingChild = await DbContext.Children.FirstOrDefaultAsync(e => e.Id == child.Id);
			if (existingChild == null)
			{
				return null;
			}
			DbContext.Entry(existingChild).CurrentValues.SetValues(child);
			await DbContext.SaveChangesAsync();
			return existingChild;
		}
	}
}
