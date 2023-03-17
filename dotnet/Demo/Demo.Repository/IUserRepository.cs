using Demo.Domain;

namespace Demo.Repository;

public interface IUserRepository
{
    UserPO SelectById(long id);
}