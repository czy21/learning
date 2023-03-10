use actix_web::{post, Responder, Result, Scope, web};
use serde::{Deserialize, Serialize};

pub fn controller() -> Scope {
    return web::scope("order")
        .service(post1);
}

#[derive(Serialize, Deserialize)]
struct OrderDTO {
    no: String,
    price: f64,
}

#[post("/post1")]
async fn post1(param: web::Json<OrderDTO>) -> Result<impl Responder> {
    Ok(web::Json(param))
}