using Model.app.domain;

namespace Networking.rpcprotocol
{
	[Serializable]
	public class Request<ID>
	{
		public RequestType? Type { get; set; }
		public Entity<ID>? Entity { get; set; }
		public IEnumerable<Entity<ID>>? Entities { get; set; }
		public string? Message { get; set; }

		public override string ToString() =>
			$"Request{{type={Type}, entity={Entity}, entities={Entities}, errorMessage={Message}}}";
	}
}
