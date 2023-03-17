using System.ComponentModel.DataAnnotations.Schema;

namespace Demo.Domain;

public class UserPO : BaseEntity
{
    [Column(name: "user_name")] public string? UserName { get; set; }
}