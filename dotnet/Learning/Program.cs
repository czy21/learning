var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
if (!builder.Environment.IsDevelopment())
{
    var configBasePath = Environment.GetEnvironmentVariable("APP_CONF");
    Console.WriteLine(configBasePath);
    if (configBasePath != null)
    {
        builder.Configuration.SetBasePath(configBasePath);
    }
}

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