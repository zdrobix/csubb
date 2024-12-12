using seminar11.service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.ui
{
	internal class Ui
	{
		private Service ServiceA;
		
		public Ui(Service service) => this.ServiceA = service;

		public void Run()
		{
            Console.WriteLine("1. Afisare angajati\n" +
				"2. Afisare pontaje\n" +
				"3. Afisare sarcini\n" +
				"4. Afisare angajati cu peste X sarcini\n");
			var option = Console.ReadLine();

			if (option == "1")
			{
				Console.WriteLine("Angajati: \n");
				foreach (var angajat in this.ServiceA.GetAngajati()!)
				{
					Console.WriteLine(angajat);
				}
			}
			if (option == "2")
			{
				Console.WriteLine("\nPontaje: \n");
				foreach (var pontaj in this.ServiceA.GetPontaje()!)
				{
					Console.WriteLine("" +
						$"({this.ServiceA.GetAngajat(pontaj.getId()!.Item1)!}) " +
						$"({this.ServiceA.GetSarcina(pontaj.getId()!.Item2)!}) " +
						$"{pontaj.DataPontaj}");
				}
			}
			if (option == "3") { 
				Console.WriteLine("\nSarcini: \n");
				foreach (var sarcina in this.ServiceA.GetSarcini()!)
				{
					Console.WriteLine(sarcina);
				}
			}
			if (option == "4")
			{
				var number = int.Parse(Console.ReadLine()!);
				Console.WriteLine($"\nAngajati cu peste {number} sarcini");
				

			}
		}
	}
}
