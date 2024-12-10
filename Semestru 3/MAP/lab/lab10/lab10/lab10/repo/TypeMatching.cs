using lab10.domain;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.repo
{
	internal class TypeMatching<E, ID> where E : Entity<ID>
	{
		public static string GetTableName()
		{
			if (typeof(E) == typeof(Elev))
			{
				return "elevi";
			}
			else if (typeof(E) == typeof(Echipa))
			{
				return "echipe";
			}
			else if (typeof(E) == typeof(Jucator))
			{
				return "jucatori";
			}
			else if (typeof(E) == typeof(JucatorActiv))
			{
				return "jucatori_activi";
			}
			else if (typeof(E) == typeof(Meci))
			{
				return "meciuri";
			}
			else throw new ArgumentException("Invalid type given.");
		}

		public static string CreateListFromEntity(E Entity)
		{
			var resultList = $"(";

			if (Entity is Elev elev)
			{
				resultList += $"\'{elev.getName()}\', ";
				resultList += $"\'{elev.getScoala()}\')";
			}
			else if (Entity is Echipa echipa)
			{
				resultList += $"\'{echipa.getName()}\')";
			}
			else if (Entity is Jucator jucator)
			{
				resultList += $"{jucator.getId()}, ";
				resultList += $"\'{jucator.getIdEchipaJucator()}\')";
			}
			else if (Entity is JucatorActiv jucatorActiv)
			{
				resultList += $"{jucatorActiv.getId()}, ";
				resultList += $"{jucatorActiv.getIdMeci()}, ";
				resultList += $"{jucatorActiv.getPuncteInscrise()}, ";
				resultList += $"\'{jucatorActiv.getTipJucator()}\')";
			}
			else if (Entity is Meci meci)
			{
				resultList += $"{meci.getId()}, ";
				resultList += $"{meci.getIdGazde()}, ";
				resultList += $"{meci.getIdOaspeti()}, ";
				resultList += $"\'{meci.getDate().ToString()}\')";
			}
			else
				throw new ArgumentException("Invalid entity type " + Entity.GetType().Name);

			return resultList;
		}

		public static E? CreateEntityFromList (List<string> args)
		{
			if (typeof(E) == typeof(Elev))
			{
				return new Elev(
					int.Parse(args[0]),
					args[1],
					args[2]
				) as E;
			}
			else if (typeof(E) == typeof(Echipa))
			{
				return new Echipa(
					int.Parse(args[0]),
					args[1]
				) as E;
			}
			else if (typeof(E) == typeof(Jucator))
			{
				return new Jucator(
					int.Parse(args[0]),
					args[1],
					args[2],
					int.Parse(args[3])
				) as E;
			}
			else if (typeof(E) == typeof(JucatorActiv))
			{
				return new JucatorActiv(
					int.Parse(args[0]),
					int.Parse(args[1]),
					int.Parse(args[2]),
					(Tip)Enum.Parse(typeof(Tip), args[3])
				) as E;
			}
			else if (typeof(E) == typeof(Meci))
			{
				return new Meci(
					int.Parse(args[0]),
					int.Parse(args[1]),
					int.Parse(args[2]),
					DateOnly.Parse(args[3])
				) as E;
			}
			else throw new ArgumentException("Invalid type given.");
		}

		public static string ReturnCondition (ID id)
		{
			if (typeof(E) == typeof(Elev) || typeof(E) == typeof(Echipa) || typeof(E) == typeof(Meci))
			{
				return "id = " + id;
			}
			else if (typeof(E) == typeof(Jucator))
			{
				return "id_elev = " + id;
			}
			else if (typeof(E) == typeof(JucatorActiv) && id is Tuple<int, int> tuple)
			{
				return "id_jucator = " + tuple.Item1 + " AND id_meci = " + tuple.Item2;
			}
			else throw new ArgumentException("Invalid type given.");
		}
	}
}
