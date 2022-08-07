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
        var list = new List<Dictionary<string, object>> { new Dictionary<string, object>() { { "name", "czy" }, { "age", "26" } } };
        return Task.FromResult(list);
    }
}
