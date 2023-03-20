using Autofac;

namespace Demo.Repository;

public class RepositoryRegister : Module
{
    protected override void Load(ContainerBuilder builder)
    {
        builder.RegisterAssemblyTypes(ThisAssembly)
            .Where(t => t.IsAssignableTo<IRepositoryBase>())
            .AsImplementedInterfaces()
            .InstancePerLifetimeScope();
    }
}