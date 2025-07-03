using Model.app.domain;

namespace Services.services_async
{
    public interface IServiceLogin
    {
        Task<LoginInfo?> GetByUsernameAsync(string username);
    }
}
