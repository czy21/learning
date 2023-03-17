using Demo.Domain;

namespace Demo.Application.Service;

public interface IUserService
{
    UserPO FindById(long id);
}