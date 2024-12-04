using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace lab9.model
{
	internal class MessageTask : Task
	{
		private string message;
		private string from;
		private string to;
		private DateTime date;

		public MessageTask(string taskId, string description, string message, string from, string to, DateTime date)
			: base(taskId, description)
		{ 
			this.message = message;
			this.from = from;
			this.to = to;
			this.date = date;
		}

		public override void execute()
		{
			Console.WriteLine(date.ToString() + ": " + message);
		}

		public string ToString()
		{
			return "id = " + getId() +
					" | description = " + getDescription() +
					" | message = " + message +
					" | from = " + from +
					" | to = " + to +
					" | date = " + date.ToString();
		}
	}
}
