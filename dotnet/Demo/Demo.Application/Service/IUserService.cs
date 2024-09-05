using Demo.Domain;

namespace Demo.Application.Service;

public interface IUserService : IServiceBase
{
    UserPO? FindById(string id);
}