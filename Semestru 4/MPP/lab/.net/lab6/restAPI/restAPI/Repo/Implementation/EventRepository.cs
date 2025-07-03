using Microsoft.EntityFrameworkCore;
using restAPI.Data;
using restAPI.Model.Domain;
using restAPI.Repo.Interface;

namespace restAPI.Repo.Implementation
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
			await DbContext.Events.AddAsync(event_);
			await DbContext.SaveChangesAsync();
			return event_;
		}

		public async Task<Event?> DeleteAsync(int id)
		{
			var existingEvent = await DbContext.Events.FirstOrDefaultAsync(e => e.Id == id);
			if (existingEvent == null)
			{
				return null;
			}
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
			{
				return null;
			}
			DbContext.Entry(existingEvent).CurrentValues.SetValues(event_);
			await DbContext.SaveChangesAsync();
			return existingEvent;
		}
	}
}
