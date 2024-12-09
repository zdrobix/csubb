using lab9.decorator;
using lab9.factory;
using lab9.model;

namespace lab9
{
	internal class Application
	{
		public static void Main (string[] args)
		{
			var stiva = new StrategyTaskRunner(Strategy.LIFO);
			var queue = new StrategyTaskRunner(Strategy.FIFO);

			var task1 = new MessageTask("task1", "Acesta este task 1", "Salut", "Alex", "Ioan", DateTime.Now);
			var task2 = new MessageTask("task2", "Acesta este task 2", "Ce faci??", "Ioan", "Alex", DateTime.Now);
			var task3 = new MessageTask("task3", "Acesta este task 3", "Bine", "Alex", "Ioan", DateTime.Now);
			var task4 = new MessageTask("task4", "Acesta este task 4", "Tu", "Ioan", "Alex", DateTime.Now);

			stiva.AddTask(task1);
			stiva.AddTask(task2);
			stiva.AddTask(task3);
			stiva.AddTask(task4);

			queue.AddTask(task1);
			queue.AddTask(task2);
			queue.AddTask(task3);
			queue.AddTask(task4);

			Console.WriteLine("\nExecutarea in stiva: ");
			stiva.ExecuteAll();

			Console.WriteLine("\nExecutarea in coada: ");
			queue.ExecuteAll();


			var sortingTask1 = new SortingTask("1", "Sortarea numerelor: 1 9 4 2 5 -1 2 6 folosing quick sort",
				SortingMethod.QuickSort, new List<int> { 1, 9, 4, 2, 5, -1, 2, 6 });
			var sortingTask2 = new SortingTask("1", "Sortarea numerelor: 1 9 4 2 5 -1 2 6 folosing bubble sort",
				SortingMethod.BubbleSort, new List<int> { 1, 9, 4, 2, 5, -1, 2, 6 });

            Console.Write("Numerele nesortate sunt: ");
			sortingTask1.getNumbers().ForEach(nr => Console.Write(nr + " "));
			Console.WriteLine();

			sortingTask1.execute();
			sortingTask2.execute();

			Console.Write("Numerele sortate descrescator sunt: \n");
			sortingTask1.getNumbers().ForEach(nr => Console.Write(nr + " "));
			Console.WriteLine();
			sortingTask2.getNumbers().ForEach(nr => Console.Write(nr + " "));
			Console.WriteLine();
		}
	}
}