using System.Text.Json.Serialization;
using Autofac;
using Autofac.Extensions.DependencyInjection;
using Consul;
using Demo.Application;
using Demo.Repository;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);
builder.Host.UseServiceProviderFactory(new AutofacServiceProviderFactory());
builder.Host.ConfigureContainer<ContainerBuilder>(b =>
{
    b.RegisterModule<RepositoryRegister>();
    b.RegisterModule<ServiceRegister>();
});
builder.Services.AddDbContext<DbMasterContext>(opt =>
{
    opt.UseMySQL(builder.Configuration.GetConnectionString("Master") ?? string.Empty);
});
// Add services to the container.
builder.Services.AddSingleton<IConsulClient>(consul =>
    new ConsulClient(consulConfig => { consulConfig.Address = new Uri("http://consul.cluster.com"); })
);
builder.Services.AddControllers().AddJsonOptions(options =>
{
    options.JsonSerializerOptions.DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull;
});
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();
var swaggerEnable = app.Configuration.GetValue<bool>("swagger:enable");
if (swaggerEnable)
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.MapControllers();

app.Run();