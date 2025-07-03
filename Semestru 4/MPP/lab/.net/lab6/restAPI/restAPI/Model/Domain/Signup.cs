namespace restAPI.Model.Domain
{
	public class Signup
	{
		public int ChildId { get; set; }
		public int EventId { get; set; }

		public Signup(int eventId, int childId) {
			this.EventId = eventId;
			this.ChildId = childId;
		}

		public Signup() { }
	}
}
