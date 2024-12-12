using seminar11.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace seminar11.repo
{
	internal class TypeMatching<E, ID> where E : Entity<ID>
	{
		public static string GetFile()
		{
			if (typeof(E) == typeof(Angajat))
				return @"Q:\info\csubb\Semestru 3\MAP\seminar\seminar11\seminar11\seminar11\input\angajat.txt";
			else if (typeof(E) == typeof(Pontaj))
				return @"Q:\info\csubb\Semestru 3\MAP\seminar\seminar11\seminar11\seminar11\input\pontaj.txt";
			else if (typeof(E) == typeof(Sarcina))
				return @"Q:\info\csubb\Semestru 3\MAP\seminar\seminar11\seminar11\seminar11\input\sarcina.txt";
			throw new ArgumentException("Invalid type.");
		}

		public static E? CreateEntityFromList (List<string> args)
		{
			if (typeof(E) == typeof(Angajat))
				return new Angajat(
					int.Parse(args[0]),
					args[1],
					double.Parse(args[2]),
					args[3]
				) as E;

			else if (typeof(E) == typeof(Pontaj))
				return new Pontaj(
					Tuple.Create(
						int.Parse(args[0]),
						args[1]
					),
					args[2]
				) as E;

			else if (typeof(E) == typeof(Sarcina))
				return new Sarcina(
					args[0],
					args[1],
					int.Parse(args[2])
				) as E;
			throw new ArgumentException("Invalid type.");
		}
	}
}
