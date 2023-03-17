using System.Reflection;
using Autofac;
using Module = Autofac.Module;

namespace Demo.Repository;

public class RepositoryRegister : Module
{
    protected override void Load(ContainerBuilder builder)
    {
        builder.RegisterAssemblyTypes(ThisAssembly)
            .Where(t => t.GetCustomAttribute<Framework.Repository>() != null)
            .AsImplementedInterfaces()
            .InstancePerLifetimeScope();
    }
}