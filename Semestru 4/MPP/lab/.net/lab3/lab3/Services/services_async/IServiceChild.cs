using Model.app.domain;


namespace Services.services_async
{
	public interface IServiceChild
	{
		Task<IEnumerable<Child>> GetAllAsync();
		Task<Child> GetByIdAsync(int id);
		Task<Child> AddAsync(string name, string cnp);
		Task<Child> UpdateAsync(Child child);
		Task<IEnumerable<Child>> GetByAgeAsync(int age);
	}
}
