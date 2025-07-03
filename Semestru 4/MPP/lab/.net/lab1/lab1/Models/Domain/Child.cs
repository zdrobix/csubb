namespace lab1.Models.Domain
{
	public class Child : Entity<int>
	{
        public string Name { get; set; }
        public string Cnp { get; set; }

        public Child(string Name, string Cnp)
        {
            this.Name = Name;
            this.Cnp = Cnp;
        }

        public Child() { }
    }
}
