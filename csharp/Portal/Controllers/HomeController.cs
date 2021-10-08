using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Portal.Models;

namespace Portal.Controllers
{
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
            var dic = new Dictionary<string, object> {{"name", "czy"}};
            var list = new List<Dictionary<string, object>> {new Dictionary<string, object>() {{"name", "czy"}, {"age", "26"}}};
            return Task.FromResult(list);
        }
    }
}