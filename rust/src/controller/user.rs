use std::collections::HashMap;
use std::iter::Map;
use actix_web::{get, web, Responder, Result, post};
use serde::{Deserialize, Serialize};

#[derive(Serialize, Deserialize)]
struct User {
    name: String,
}

#[post("/user/search")]
pub async fn search(param: web::Json<HashMap<String, String>>) -> Result<impl Responder> {
    Ok(web::Json(User {
        name: "a".to_string()
    }))
}

#[get("/user/{name}")]
pub async fn detail(name: web::Path<String>) -> Result<impl Responder> {
    Ok(web::Json(User {
        name: name.to_string()
    }))
}
