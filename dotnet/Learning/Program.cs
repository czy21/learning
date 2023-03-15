using Consul;
using Microsoft.Extensions.DependencyInjection.Extensions;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddSingleton<IConsulClient>(consul =>
    new ConsulClient(consulConfig =>
    {
        consulConfig.Address = new Uri("http://consul.cluster.com");
    }));
builder.Services.AddControllers();
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