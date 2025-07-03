using Model.app.domain;
using Persistence.repo_async;
using Services.services_async;

namespace Server.app.service_async
{
	public class ServiceSignup : IServiceSignup
	{
		private readonly ISignupRepository signupRepository;

		public ServiceSignup(ISignupRepository signupRepository)
		{
			signupRepository = signupRepository;
		}

		public async Task<Signup?> Add(Child child, Event event_) =>
		  await signupRepository.CreateAsync(new Signup(child, event_));

		public async Task<IEnumerable<Signup>> GetAll() =>
			await signupRepository.GetAllAsync();

		public async Task<IEnumerable<Signup>?> GetByChild(int childId) =>
			await signupRepository.GetByChildId(childId);

		public async Task<IEnumerable<Signup>?> GetByEvent(int eventId) =>
						await signupRepository.GetByEventId(eventId);

		public async Task<Signup?> GetByChildIdAndEventId(int childId, int eventId) =>
				await signupRepository.GetByChildIdAndEventId(childId, eventId);

		public async Task<Signup?> AddAsync(Signup signupData) =>
			await signupRepository.CreateAsync(signupData);

		public async Task<Signup?> Update(Signup signup)
		{
			throw new NotImplementedException();
		}
	}
}
