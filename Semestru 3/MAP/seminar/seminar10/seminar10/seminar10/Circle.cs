using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace geometry
{
	internal class Circle : IShape
	{
		public double radius { get; init; }
		public Circle (double radius_)
		{
			this.radius = radius_;
		}
		public double computeArea() => Math.PI * this.radius * this.radius;

		public override string ToString() => $"Circle with radius {this.radius}";
	}
}
