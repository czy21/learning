using Demo.Domain;

namespace Demo.Repository;

public interface IUserRepository:IRepositoryBase
{
    UserPO SelectById(long id);
}