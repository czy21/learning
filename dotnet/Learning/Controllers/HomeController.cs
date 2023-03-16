using Consul;
using Microsoft.AspNetCore.Mvc;
using YamlDotNet.Serialization;

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
}