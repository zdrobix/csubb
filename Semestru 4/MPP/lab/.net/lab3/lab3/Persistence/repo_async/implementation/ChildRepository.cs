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
	public class ChildRepository : IChildRepository
	{
		private readonly AppDbContext DbContext;

		public ChildRepository(AppDbContext dbContext)
		{
			DbContext = dbContext;
		}

		public async Task<Child> CreateAsync(Child child)
		{
			await DbContext.AddAsync(child);
			await DbContext.SaveChangesAsync();
			return child;
		}

		public async Task<Child?> DeleteAsync(int id)
		{
			var child = await DbContext.Children.FindAsync(id);
			if (child == null)
				throw new EntryPointNotFoundException("Child not found");
			DbContext.Children.Remove(child);
			await DbContext.SaveChangesAsync();
			return child;
		}

		public async Task<IEnumerable<Child>> GetAllAsync() =>
			await DbContext.Children.ToListAsync();

		public async Task<Child?> GetByIdAsync(int id) =>
			await DbContext.Children.FirstOrDefaultAsync(c => c.Id == id);

		public async Task<Child?> UpdateAsync(Child child)
		{
			var existingChild = await DbContext.Children.FindAsync(child.Id);
			if (existingChild == null)
				return null;
			DbContext.Entry(existingChild).CurrentValues.SetValues(child);
			await DbContext.SaveChangesAsync();
			return existingChild;
		}
	}
}
