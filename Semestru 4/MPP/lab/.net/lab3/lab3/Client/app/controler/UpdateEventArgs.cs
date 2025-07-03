using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.app.controler
{
	public class UpdateEventArgs : EventArgs
	{
		public UpdateType Type { get; }
		public object Data { get; }

		public UpdateEventArgs(UpdateType type, object data)
		{
			Type = type;
			Data = data;
		}
	}
}
