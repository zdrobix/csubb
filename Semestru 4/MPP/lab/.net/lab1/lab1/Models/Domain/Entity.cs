namespace lab1.Models.Domain
{
	public class Entity<ID>
	{
		public ID Id { get; set; }

        public Entity()
        {
                
        }

        public Entity<ID> SetId (ID id)
		{
			this.Id = id;
			return this;
		}
	}
}
