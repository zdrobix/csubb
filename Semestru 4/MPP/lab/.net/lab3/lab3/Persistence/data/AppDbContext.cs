using Microsoft.EntityFrameworkCore;
using Model.app.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistence.data
{
	public class AppDbContext : DbContext
	{

		AppDbContext()
		{
		}

		public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
		{
		}

		protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
		{
			if (!optionsBuilder.IsConfigured)
			{
				optionsBuilder.UseSqlite("Data Source=app.db");
			}
		}

		protected override void OnModelCreating(ModelBuilder modelBuilder)
		{
			base.OnModelCreating(modelBuilder);

			modelBuilder.Entity<Signup>()
				.Ignore(s => s.Id)
				.HasNoKey();
		}

		public DbSet<Child> Children { get; set; }
		public DbSet<Event> Events { get; set; }
		public DbSet<LoginInfo> Logins { get; set; }
		public DbSet<Signup> Signups { get; set; }
	}
}
