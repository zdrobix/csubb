namespace restAPI.Model.Domain
{
	public class Child : Entity<int>
	{
		public string Name { get; set; }
		public string Cnp { get; set; }

		public Child(string name, string cnp)
		{
			this.Name = name;
			this.Cnp = cnp;
		}

		public Child() { }
	}
}
