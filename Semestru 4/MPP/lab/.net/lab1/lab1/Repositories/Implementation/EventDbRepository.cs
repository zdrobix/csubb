using lab1.Data;
using lab1.Models.Domain;
using lab1.Repositories.Interface;
using System.ComponentModel.Design;
using System.Diagnostics;
using Microsoft.EntityFrameworkCore;
using log4net;

namespace lab1.Repositories.Implementation
{
	public class EventDbRepository : IEventRepository
	{

		private readonly ApplicationDbContext dbContext;
		private static readonly ILog _logger = LogManager.GetLogger(typeof(ChildDbRepository));

		public EventDbRepository(ApplicationDbContext dbContext)
		{
			_logger.Info($"Initialized EventDbRepository.");
			this.dbContext = dbContext;
		}

		public async Task<Event> CreateAsync(Event e)
		{
			_logger.Info($"Created event {e.Name}");
			await dbContext.Events.AddAsync(e);
			await dbContext.SaveChangesAsync();
			return e;
		}

		public async Task<Event?> DeleteAsync(int id)
		{
			_logger.Info($"Deleted event with id: {id}");
			var eventt = await dbContext.Events.FirstOrDefaultAsync(e => e.Id == id);
			if (eventt == null)
				return null;
			dbContext.Events.Remove(eventt);
			await dbContext.SaveChangesAsync();
			return eventt;
		}

		public async Task<IEnumerable<Event>> GetAllAsync()
		{
			_logger.Info($"Getting all events");
			return await dbContext.Events.ToListAsync();
		}

		public async Task<IEnumerable<Event>> GetAllByMaxAge(int maxAge)
		{
			_logger.Info($"Getting all events with max age: {maxAge}");
			return await dbContext.Events.Where(e => e.MaxAge == maxAge).ToListAsync();
		}

		public async Task<IEnumerable<Event>> GetAllByMinAge(int minAge)
		{
			_logger.Info($"Getting all events with min age: {minAge}");
			return	await dbContext.Events.Where(e => e.MinAge == minAge).ToListAsync();
		}

		public async Task<Event?> GetById(int id)
		{
			_logger.Info($"Getting event with id: {id}");
			return	await dbContext.Events.FirstOrDefaultAsync(e => e.Id == id);
		}

		public async Task<Event?> UpdateAsync(Event e)
		{
			_logger.Info($"Updating event with id: {e.Id}");
			var eventt = dbContext.Events.FirstOrDefault(e => e.Id == e.Id);
			if (eventt == null)
				return null;
			dbContext.Entry(eventt).CurrentValues.SetValues(e);
			await dbContext.SaveChangesAsync();
			return eventt;
		}
	}
}
