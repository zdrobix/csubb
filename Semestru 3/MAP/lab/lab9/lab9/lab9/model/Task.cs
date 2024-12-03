using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace lab9.model
{
	internal abstract class Task
	{
		private string id;
		private string description;
		public Task(string id, string description)
		{
			this.id = id;
			this.description = description;
		}

		public string getId() => this.id;
		public string getDescription() => this.description;
		public void setId(string id) => this.id = id;
		public void setDescription(string description) => this.description = description;
		public abstract void execute();
		public string toString()
		{
			return "Task{" +
					"id='" + id + '\'' +
					", description='" + description + '\'' +
					'}';
		}

		public bool equals(object o)
		{
			if (this == o) return true;
			if (o is Task task)
				return id.Equals(task.id) && description.Equals(task.description);
			return false;
		}

	}
}
