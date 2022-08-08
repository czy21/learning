using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using Learning.Models;

namespace Learning.Controllers;

public class HomeController : Controller
{
    private readonly ILogger<HomeController> _logger;

    public HomeController(ILogger<HomeController> logger)
    {
        _logger = logger;
    }

    [HttpGet("list")]
    public Task<List<Dictionary<string, object>>> List()
    {
        var dic = new Dictionary<string, object> { { "name", "czy" } };
        var list = new List<Dictionary<string, object>> { dic };
        return Task.FromResult(list);
    }

    [HttpGet(template:"list2")]
    public Task<List<Dictionary<string, object>>> List2()
    {
        var dic = new Dictionary<string, object>() { { "name", "ha" } };
        _logger.LogInformation("hahaha");
        return Task.FromResult(new List<Dictionary<string, object>> {dic});
    }
}
