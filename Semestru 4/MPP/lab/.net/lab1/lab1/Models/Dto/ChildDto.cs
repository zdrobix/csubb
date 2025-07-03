using lab1.Models.Domain;

namespace lab1.Models.Dto
{
	public class ChildDto : Entity<int>
	{
        public string Name { get; set; }
        public string Cnp { get; set; }
    }
}
