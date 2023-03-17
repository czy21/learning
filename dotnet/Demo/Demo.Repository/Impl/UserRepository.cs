using Demo.Domain;

namespace Demo.Repository.Impl;

[Framework.Repository]
public class UserRepository : IUserRepository
{
    public UserPO SelectById(long id)
    {
        return new UserPO();
    }
}