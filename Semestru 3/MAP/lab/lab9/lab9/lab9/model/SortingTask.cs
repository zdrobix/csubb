using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace lab9.model
{
	internal class SortingTask : Task
	{
		private SortingMethod method;
		private List<int> numbersVector;

		public SortingTask(string id, string description, SortingMethod method_, List<int> numbersVector_)
			: base(id, description)
		{
			this.method = method_;
			this.numbersVector = numbersVector_;
		}

		private int partition(List<int> list, int low, int high)
		{
			int pivot = list[high];
			int i = low - 1;
			for (int j = low; j < high; j++)
			{
				if (list[j] > pivot)
				{
					i++;
					int temp = list[i];
					list[i] = list[j];
					list[j] = temp;
				}
			}
			int aux = list[i+1];
			list[i+1] =  list[high];
			list[high] = aux;
			return i + 1;
		}

		private void quickSort(List<int> list, int low, int high)
		{
			if (low < high)
			{
				int pivot = partition(list, low, high);
				quickSort(list, low, pivot - 1);
				quickSort(list, pivot + 1, high);
			}
		}

		private void bubbleSort(List<int> list)
		{
			bool swap;
			for (int i = 0; i < list.Count - 1; i++)
			{
				swap = false;
				for (int j = 0; j < list.Count - 1 - i; j++)
					if (list[j] < list[j + 1])
					{
						var aux = list[j];
						list[j] = list[j + 1];
						list[j + 1] = aux;
						swap = true;
					}
				if (!swap)
					break;
			}
		}

		public List<int> getNumbers() => this.numbersVector;

		public override void execute()
		{
			switch (this.method)
			{
				case SortingMethod.BubbleSort: 
					this.bubbleSort(this.numbersVector);
					break;
				case SortingMethod.QuickSort: 
					this.quickSort(this.numbersVector, 0, this.numbersVector.Count - 1);
					break;
				default: throw new ArgumentException("Unexpected value: " + this.method);
			}
		}
	}
}
