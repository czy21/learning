using Consul;
using Demo.Application.Service;
using Demo.Domain;
using Microsoft.AspNetCore.Mvc;
using YamlDotNet.Serialization;

namespace Demo.Portal.Controllers;

public class UserController : Controller
{
    private readonly ILogger<UserController> _logger;
    private readonly IConsulClient _consulClient;
    private readonly IUserService _userService;

    public UserController(ILogger<UserController> logger, IConsulClient consulClient, IUserService userService)
    {
        _logger = logger;
        _consulClient = consulClient;
        _userService = userService;
    }

    [HttpGet("list")]
    public Task<List<Dictionary<string, object>>> List()
    {
        var dic = new Dictionary<string, object> { { "name", "czy" } };
        var list = new List<Dictionary<string, object>> { dic };
        var kvBytes = _consulClient.KV.Get("demo-common/application/data").Result.Response.Value;
        var kvStr = System.Text.Encoding.UTF8.GetString(kvBytes);
        var kvObj = new Deserializer().Deserialize<Dictionary<object, object>>(new StringReader(kvStr));
        // var serverPort = ((Dictionary<object, object>)kvObj["server"])["port"];
        return Task.FromResult(list);
    }

    [HttpGet(template: "list2")]
    public Task<List<Dictionary<string, object>>> List2()
    {
        var dic = new Dictionary<string, object>() { { "name", "ha" } };
        _logger.LogInformation("hahaha");
        return Task.FromResult(new List<Dictionary<string, object>> { dic });
    }

    [HttpGet(template: "detail1")]
    public Task<UserPO?> Detail1()
    {
        return Task.FromResult(_userService.FindById("8d9ab777-568c-11ed-8985-0242ac120010"));
    }
}