using Autofac;

namespace Demo.Application;

public class ServiceRegister : Module
{
    protected override void Load(ContainerBuilder builder)
    {
        builder.RegisterAssemblyTypes(ThisAssembly)
            .Where(t => t.IsAssignableTo<IServiceBase>())
            .AsImplementedInterfaces()
            .InstancePerLifetimeScope();
    }
}