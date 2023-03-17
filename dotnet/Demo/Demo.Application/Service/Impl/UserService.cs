using Demo.Domain;
using Demo.Repository;

namespace Demo.Application.Service.Impl;

[Framework.Service]
public class UserService : IUserService
{
    private readonly IUserRepository _userRepository;

    public UserService(IUserRepository userRepository)
    {
        _userRepository = userRepository;
    }

    public UserPO FindById(long id)
    {
        return _userRepository.SelectById(id);
    }
}