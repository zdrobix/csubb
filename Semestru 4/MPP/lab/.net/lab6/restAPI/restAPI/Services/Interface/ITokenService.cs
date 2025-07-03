using restAPI.Model.Domain;

namespace restAPI.Services.Interface
{
	public interface ITokenService
	{
		string GenerateToken(LoginInfo user);
	}
}
