using Demo.Domain;
using Microsoft.EntityFrameworkCore;

namespace Demo.Repository;

public class DbMasterContext : DbContext
{
    public DbMasterContext(DbContextOptions<DbMasterContext> options) : base(options)
    {
    }

    public DbSet<UserPO?> Users { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<UserPO>().ToTable("ent_sys_user");
    }
}