using Demo.Domain;

namespace Demo.Repository.Impl;

public class UserRepository : IUserRepository
{
    public UserPO SelectById(long id)
    {
        return new UserPO();
    }
}