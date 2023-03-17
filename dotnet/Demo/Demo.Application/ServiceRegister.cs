using System.Reflection;
using Autofac;
using Module = Autofac.Module;

namespace Demo.Application;

public class ServiceRegister : Module
{
    protected override void Load(ContainerBuilder builder)
    {
        builder.RegisterAssemblyTypes(ThisAssembly)
            .Where(t => t.GetCustomAttribute<Framework.Service>() != null)
            .AsImplementedInterfaces()
            .InstancePerLifetimeScope();
    }
}