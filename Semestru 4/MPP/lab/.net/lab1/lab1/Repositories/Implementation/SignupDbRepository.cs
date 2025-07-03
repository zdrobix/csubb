using lab1.Data;
using lab1.Models.Domain;
using lab1.Repositories.Interface;
using log4net;
using Microsoft.EntityFrameworkCore;

namespace lab1.Repositories.Implementation
{
	public class SignupDbRepository : ISignupRepository
	{

		private readonly ApplicationDbContext dbContext;
		private static readonly ILog _logger = LogManager.GetLogger(typeof(ChildDbRepository));

		public SignupDbRepository(ApplicationDbContext dbContext)
		{
			this.dbContext = dbContext;
		}

		public async Task<Signup> CreateAsync(Signup e)
		{
			_logger.Info($"Created signup for {e.Child.Name} at {e.Event.Name}");
			await dbContext.Signups.AddAsync(e);
			await dbContext.SaveChangesAsync();
			return e;
		}

		public async Task<Signup?> DeleteAsync(Tuple<int, int> id)
		{
			_logger.Info($"Deleted signup for {id.Item1} at {id.Item2}");
			var signup = await dbContext.Signups.FirstOrDefaultAsync(e => e.Child.Id == id.Item1 && e.Event.Id == id.Item2);
			if (signup == null)
				return null;
			dbContext.Signups.Remove(signup);
			await dbContext.SaveChangesAsync();
			return signup;
		}

		public async Task<IEnumerable<Signup>> GetAllAsync()
		{
			_logger.Info($"Getting all signups");
			return	await dbContext.Signups.ToListAsync();
		}

		public async Task<IEnumerable<Signup>> GetAllByChildId(int id)
		{
			_logger.Info($"Getting all signups for child with id: {id}");
			return	await dbContext.Signups.Where(e => e.Child.Id == id).ToListAsync();
		}

		public async Task<IEnumerable<Signup>> GetAllByEventId(int id)
		{
			_logger.Info($"Getting all signups for event with id: {id}");
			return	await dbContext.Signups.Where(e => e.Event.Id == id).ToListAsync();
		}

		public async Task<Signup?> GetById(Tuple<int, int> id)
		{
			_logger.Info($"Getting signup for {id.Item1} at {id.Item2}");
			return	await dbContext.Signups.FirstOrDefaultAsync(e => e.Child.Id == id.Item1 && e.Event.Id == id.Item2);
		}


		public async Task<Signup?> UpdateAsync(Signup e)
		{
			_logger.Info($"Updating signup for {e.Child.Name} at {e.Event.Name}");
			var signup = await dbContext.Signups.FirstOrDefaultAsync(signup => signup.Child.Id == e.Child.Id && signup.Event.Id == e.Event.Id);
			if (signup == null)
				return null;
			dbContext.Entry(signup).CurrentValues.SetValues(e);
			return signup;
		}
	}
}
