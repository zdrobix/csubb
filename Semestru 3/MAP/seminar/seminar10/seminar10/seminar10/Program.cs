namespace geometry
{
	internal class Application ()
	{
		public static void Main(string[] args)
		{
			var circle = new Circle(10);
			var square = new Square(10);

            Console.WriteLine($"The area of the the circle is {circle.computeArea()}");
            Console.WriteLine($"The area of the the square is {square.computeArea()}");

			var listCircles = new List<Circle>();
			var listSquares = new List<Square>();

			for (int i = 0; i < 10; i++)
			{
				listCircles.Add(new Circle(i));
				listSquares.Add(new Square(i));
			}

			foreach (var c in listCircles) 
				Console.WriteLine($"{c} Area: {c.computeArea()}");
			foreach (var s in listSquares)
				Console.WriteLine($"{s} Area: {s.computeArea()}");

            Console.WriteLine("Enter a radius :");
			double radius;
			Double.TryParse(Console.ReadLine(), out radius);

			var filteredList = new List<Circle>();

			foreach (var c in listCircles)
				if (c.radius <  radius)
					filteredList.Add(c);

			foreach (var c in filteredList)
				Console.WriteLine($"{c} Area: {c.computeArea()}");

		}
	}
}