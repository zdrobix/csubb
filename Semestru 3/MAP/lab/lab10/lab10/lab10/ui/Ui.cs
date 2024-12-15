using lab10.service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.ui
{
	internal class Ui
	{
		private Service ServiceApp = new Service();
		public Ui() { }

		public void Run()
		{
            Console.WriteLine("Bun venit in aplicatie.");
            while (true)
			{
				bool exit = false; 
                Console.WriteLine("\n1) Toti jucatorii unei echipe date" +
					"\n2) Toti jucatorii activi de la un meci anume" +
					"\n3) Toate meciurile dintr-o perioada" +
					"\n4) Scorul unui meci anume" +
					"\n5) Exit" +
					"\nAlege o optiune: ");
				var option = Console.ReadLine()!.Trim();
				switch (option)
				{
					case "1":
						{
							Console.WriteLine("Acestea sunt echipele:");
							Console.WriteLine('\n' + string.Join('\n',
												this.ServiceApp.GetEchipe().Select(
															x => $"{x.getId()}) {x.getName()}"
												)) + "\nIntroduceti id-ul echipei dorite:");
							int id;
							int.TryParse(Console.ReadLine(), out id);
							Console.WriteLine('\n' + string.Join('\n',
												this.ServiceApp.GetJucatori()
														.Where(
															x => x.getIdEchipaJucator() == id
														).Select(
															jucator => $"{jucator.getName(),-25} {jucator.getScoala()}"
														)));
							break;
						}
					case "2":
						{
							Console.WriteLine("Acestea sunt meciurile:");
							Console.WriteLine('\n' + string.Join('\n',
												this.ServiceApp.GetMeciuri().Select(
															x => $"{x.getId()}) {this.ServiceApp.GetEchipa(x.getIdGazde())} - " +
															$"{this.ServiceApp.GetEchipa(x.getIdOaspeti())}  {x.getDate()}"
												)) + "\nIntroduceti id-ul meciului dorit:");
							int id;
							int.TryParse(Console.ReadLine(), out id);
							Console.WriteLine('\n' + string.Join('\n',
												this.ServiceApp.GetJucatoriActivi()
														.Where(
															x => x.getIdMeci() == id
														).Select(
															jucator => $"{this.ServiceApp.GetElev(jucator.getId().Item1)!.getName(),-25} puncte: {jucator.getPuncteInscrise()}"
														)));
							break;
						}
					case "3":
						{
							DateOnly first, last;
							Console.WriteLine("\nIntroduceti prima data: ");
							DateOnly.TryParse(Console.ReadLine(), out first);
                            Console.WriteLine("\nIntroduceti a doua data: ");
							DateOnly.TryParse(Console.ReadLine(), out last);
							if (first > last)
                                Console.WriteLine("Invalid dates.");
							Console.WriteLine('\n' + string.Join('\n',
												this.ServiceApp.GetMeciuri()
												.Where(x => x.getDate() >= first && x.getDate() <= last)
												.Select(
															x => $"{x.getId()}) {this.ServiceApp.GetEchipa(x.getIdGazde())} - " +
															$"{this.ServiceApp.GetEchipa(x.getIdOaspeti())}  {x.getDate()}"
												)));
							break;
						}
					case "4":
						{
							Console.WriteLine("Acestea sunt meciurile:");
							Console.WriteLine('\n' + string.Join('\n',
												this.ServiceApp.GetMeciuri().Select(
															x => $"{x.getId()}) {this.ServiceApp.GetEchipa(x.getIdGazde())} - " +
															$"{this.ServiceApp.GetEchipa(x.getIdOaspeti())}  {x.getDate()}"
												)) + "\nIntroduceti id-ul meciului dorit:");
							int id;
							int.TryParse(Console.ReadLine(), out id);
							Console.WriteLine("Scorul meciului este "
								+ this.ServiceApp.GetJucatoriActivi()
										.Where(j => j.getIdMeci() == id)
										.Where(j => this.ServiceApp.GetJucator(j.getIdJucator())!.getIdEchipaJucator() == this.ServiceApp.GetMeci(id)!.getIdGazde())
										.Sum(j => j.getPuncteInscrise())
								+ " - " 
								+ this.ServiceApp.GetJucatoriActivi()
										.Where(j => j.getIdMeci() == id)
										.Where(j => j.getIdMeci() == id)
										.Where(j => this.ServiceApp.GetJucator(j.getIdJucator())!.getIdEchipaJucator() == this.ServiceApp.GetMeci(id)!.getIdOaspeti())
										.Sum(j => j.getPuncteInscrise())
								);
							break;
						}
					case "5":
						exit = true;
						break;
					default: break;
				}
				if (exit)
					break;
			}

		}
	}
}
