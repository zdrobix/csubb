using lab1.Data;
using lab1.Models.Domain;
using lab1.Repositories.Interface;
using log4net;
using Microsoft.EntityFrameworkCore;

namespace lab1.Repositories.Implementation
{
	public class ChildDbRepository : IChildRepository
	{
		private readonly ApplicationDbContext dbContext;
		private static readonly ILog _logger = LogManager.GetLogger(typeof(ChildDbRepository));
		public ChildDbRepository(ApplicationDbContext dbContext)
		{
			_logger.Info("Initialized childDbRepository");
			this.dbContext = dbContext;
		}

		public async Task<Child> CreateAsync(Child e)
		{
			_logger.Info($"Adding new child: {e.Name}");
			await dbContext.Children.AddAsync(e);
			await dbContext.SaveChangesAsync();
			return e;
		}

		public async Task<Child?> DeleteAsync(int id)
		{
			_logger.Info($"Removing child with id: {id}");
			var child = await dbContext.Children.FirstOrDefaultAsync(c => c.Id == id);
			if (child == null)
				return null;
			dbContext.Children.Remove(child);
			await dbContext.SaveChangesAsync();
			return child;
		}

		public async Task<IEnumerable<Child>> GetAllAsync()
		{
			_logger.Info($"Getting all children");
			return await dbContext.Children.ToListAsync();
		}

		public Task<IEnumerable<Child>> GetAllByAge(int age)
		{
			throw new NotImplementedException();
		}

		public async Task<Child?> GetById(int id)
		{
			_logger.Info($"Getting child with id: {id}");
			return await dbContext.Children.FirstOrDefaultAsync(c => c.Id == id);
		}

		public async Task<Child?> UpdateAsync(Child e)
		{
			_logger.Info($"Updating child with id: {e.Id}");
			var child = await dbContext.Children.FirstOrDefaultAsync(c => c.Id == e.Id);
			if (child == null)
				return null;
			dbContext.Entry(child).CurrentValues.SetValues(e);
			await dbContext.SaveChangesAsync();
			return child;
		}
	}
}
