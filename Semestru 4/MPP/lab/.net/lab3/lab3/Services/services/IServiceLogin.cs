using Model.app.domain;

namespace Services.services
{
    public interface IServiceLogin
    {
        LoginInfo GetByUsername(string username);
    }
}
