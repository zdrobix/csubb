using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab9
{
	internal class NumarComplex
	{
		public double re { get; init; }
		public double im { get; init; }
		public NumarComplex(double re, double im)
		{
			this.re = re;
			this.im = im;
		}
	}
}
