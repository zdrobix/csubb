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
	public class EventRepository : IEventRepository
	{
		private readonly AppDbContext DbContext;

		public EventRepository(AppDbContext dbContext)
		{
			DbContext = dbContext;
		}

		public async Task<Event> CreateAsync(Event event_)
		{
			await DbContext.AddAsync(event_);
			await DbContext.SaveChangesAsync();
			return event_;
		}

		public async Task<Event?> DeleteAsync(int id)
		{
			var existingEvent = await DbContext.Events.FindAsync(id);
			if (existingEvent == null)
				throw new EntryPointNotFoundException("Event not found");
			DbContext.Events.Remove(existingEvent);
			await DbContext.SaveChangesAsync();
			return existingEvent;
		}

		public async Task<IEnumerable<Event>> GetAllAsync() =>
			await DbContext.Events.ToListAsync();

		public async Task<Event?> GetByIdAsync(int id) =>
			await DbContext.Events.FirstOrDefaultAsync(e => e.Id == id);

		public async Task<Event?> UpdateAsync(Event event_)
		{
			var existingEvent = await DbContext.Events.FirstOrDefaultAsync(e => e.Id == event_.Id);
			if (existingEvent == null)
				throw new EntryPointNotFoundException("Event not found");
			DbContext.Entry(existingEvent).CurrentValues.SetValues(event_);
			await DbContext.SaveChangesAsync();
			return existingEvent;
		}
	}
}
