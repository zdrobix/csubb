using AlegeLitere.Model.Domain;
using Microsoft.EntityFrameworkCore;

namespace AlegeLitere.Data
{
	public class ApplicationDbContext : DbContext
	{
		public ApplicationDbContext(DbContextOptions options) : base(options)
		{

		}

		public DbSet<User> Users { get; set; }
		public DbSet<GameConfig> GameConfigurations{ get; set; }
		public DbSet<Game> Games { get; set; }
	}
}
