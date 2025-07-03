using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.rpcprotocol
{
	public enum ResponseType
	{
		OK, ERROR, LOGIN_RECEIVED, CHILD_ADDED, EVENT_ADDED, SIGNUP_ADDED, CHILDREN_RECEIVED, EVENTS_RECEIVED, SIGNUPS_RECEIVED
	}
}
