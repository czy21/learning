using System.ComponentModel.DataAnnotations.Schema;

namespace Demo.Domain;

public class UserPO
{
    [Column(name: "id")] public string? Id { get; set; }
    [Column(name: "user_name")] public string? UserName { get; set; }
    [Column(name: "create_time")] public DateTime? CreateTime { get; set; }
}