using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace lab9
{
	internal class StringToComplex
	{
		public static NumarComplex Parse (string str)
		{
			string pattern = "([+-]?[1-9]?[0-9]*)([+-]?[1-9]?[0-9]*)\\*i";

            if (Regex.IsMatch(str, pattern))
			{
                Console.WriteLine("Valid");
			} else Console.WriteLine("invalid");

			return new NumarComplex(0, 0);
		}
	}
}
