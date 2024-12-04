using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace geometry
{
	internal class Square : IShape
	{
		public double side { get; init; }

		public Square (double side_)
		{
			this.side = side_;
		}
		public double computeArea() => this.side * this.side;

		public override string ToString() => $"Square with radius {this.side}";
	}
}
