namespace restAPI.Model.Domain
{
	public class LoginInfo : Entity<int>
	{
		public string Username { get; set; }
		public string Password { get; set; }

		public LoginInfo(string username, string password)
		{
			this.Username = username;
			this.Password = password;
		}

		public LoginInfo() { }
	}
}
