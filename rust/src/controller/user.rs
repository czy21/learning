use std::collections::HashMap;
use actix_web::{get, web, Responder, Result, post};
use serde::{Deserialize, Serialize};
use serde_json::{json, Value};

#[derive(Serialize, Deserialize)]
struct User {
    name: String,
}

#[post("/user/post1")]
pub async fn post1(param: web::Json<HashMap<String, Value>>) -> Result<impl Responder> {
    let _name = json!(param.get("name"));
    Ok(web::Json(User {
        name: _name.to_string()
    }))
}

#[post("/user/post2")]
pub async fn post2(param: web::Json<HashMap<String, Value>>) -> Result<impl Responder> {
    let _age = param.get("age").unwrap();
    println!("{}", _age);
    Ok(web::Json(param))
}

#[get("/user/get1")]
pub async fn get1(query: web::Query<HashMap<String, Value>>) -> Result<impl Responder> {
    let _name = query.get("name").unwrap();
    let _age = query.get("age").unwrap();
    println!("{}", _name);
    println!("{}", _age);
    Ok(web::Json(User {
        name: String::from("aaa")
    }))
}

#[get("/user/{name}")]
pub async fn detail(name: web::Path<String>) -> Result<impl Responder> {
    Ok(web::Json(User {
        name: name.to_string()
    }))
}
