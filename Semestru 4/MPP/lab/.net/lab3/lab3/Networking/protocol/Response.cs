using Model.app.domain;

namespace Networking.rpcprotocol
{
	[Serializable]
	public class Response<ID> {
		public ResponseType? Type { get; set; }
		public Entity<ID>? Entity { get; set; }
		public IEnumerable<Entity<ID>>? Entities { get; set; }
		public string? ErrorMessage { get; set; }

		public override string ToString() =>
			$"Response{{type={Type}, entity={Entity}, entities={Entities}, errorMessage={ErrorMessage}}}";
	}
}
