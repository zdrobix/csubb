using lab1.Models.Domain;
using Microsoft.EntityFrameworkCore;

namespace lab1.Data
{
	public class ApplicationDbContext : DbContext
	{
		public ApplicationDbContext (DbContextOptions options) : base(options)
		{
		}

		public DbSet<Child> Children { get; set; }
		public DbSet<Event> Events { get; set; }
		//public DbSet<Signup> Signups { get; set; }
	}
}
