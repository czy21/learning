using Demo.Domain;


namespace Demo.Repository.Impl;

public class UserRepository : IUserRepository
{
    private readonly DbMasterContext _dbContext;

    public UserRepository(DbMasterContext dbContext)
    {
        _dbContext = dbContext;
    }

    public UserPO? SelectById(string id)
    {
        return _dbContext.Users.FirstOrDefault(t => t!.Id == id);
    }
}