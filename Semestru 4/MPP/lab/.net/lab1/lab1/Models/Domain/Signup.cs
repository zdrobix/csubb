namespace lab1.Models.Domain
{
	public class Signup : Entity<Tuple<int, int>>
	{
        public Child Child { get; set; }
        public Event Event { get; set; }

        public Signup(Child child, Event event_)
        {
            this.Child = child;
            this.Event = event_;
		}

        public Signup() { }
    }
}
