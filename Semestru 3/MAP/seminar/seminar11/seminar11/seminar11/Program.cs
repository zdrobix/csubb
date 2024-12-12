using seminar11.service;
using seminar11.ui;

namespace seminar11
{
	internal class Application
	{
		public static void Main(string[] args)
		{
			new Ui(new Service()).Run();
		}
	}
}