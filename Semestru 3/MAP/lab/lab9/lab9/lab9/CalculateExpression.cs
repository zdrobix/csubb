using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab9
{
	internal class CalculateExpression
	{
		public static void Calculate(string[] args)
		{
            Console.WriteLine("Calculating expression: " + args.Select(op => op.ToString()) + "\n");
			StringToComplex.Parse("+1000-22*i");
			StringToComplex.Parse("-200+02*i");
			StringToComplex.Parse("+1593-100*i");
			StringToComplex.Parse("1593-100");
			StringToComplex.Parse("0593-i");
			StringToComplex.Parse("-0593-i*i");
		}
	}
}
