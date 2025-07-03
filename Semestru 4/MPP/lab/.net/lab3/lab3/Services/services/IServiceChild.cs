using Model.app.domain;


namespace Services.services
{
	public interface IServiceChild
	{
		IEnumerable<Child> GetAll();
		Child GetById(int id);
		Child Add(string name, string cnp);
		Child Update(Child child);
		IEnumerable<Child> GetByAge(int age);
	}
}
