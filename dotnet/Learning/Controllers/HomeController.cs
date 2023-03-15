using Consul;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace Learning.Controllers;

public class HomeController : Controller
{
    private readonly ILogger<HomeController> _logger;
    private readonly IConsulClient _consulClient;

    public HomeController(ILogger<HomeController> logger, IConsulClient consulClient)
    {
        _logger = logger;
        _consulClient = consulClient;
    }

    [HttpGet("list")]
    public Task<List<Dictionary<string, object>>> List()
    {
        var dic = new Dictionary<string, object> { { "name", "czy" } };
        var list = new List<Dictionary<string, object>> { dic };
        var kvPair = _consulClient.KV.Get("demo-common/application/data").Result.Response;
        var p=kvPair.Value.GetValue(0);
        return Task.FromResult(list);
    }

    [HttpGet(template: "list2")]
    public Task<List<Dictionary<string, object>>> List2()
    {
        var dic = new Dictionary<string, object>() { { "name", "ha" } };
        _logger.LogInformation("hahaha");
        return Task.FromResult(new List<Dictionary<string, object>> { dic });
    }
}