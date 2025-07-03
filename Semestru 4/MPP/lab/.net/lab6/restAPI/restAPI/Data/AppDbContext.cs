using Microsoft.EntityFrameworkCore;
using restAPI.Model.Domain;

namespace restAPI.Data
{
	public class AppDbContext : DbContext
	{
		public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
		{
		}

		protected override void OnModelCreating (ModelBuilder modelBuilder)
		{
			modelBuilder.Entity<Signup>().HasNoKey();
			base.OnModelCreating(modelBuilder);
		}

		public DbSet<Event> Events { get; set; }
		public DbSet<Child> Children{ get; set; }
		public DbSet<Signup> Signups { get; set; }
		public DbSet<LoginInfo> Logins { get; set; }
	}
}
