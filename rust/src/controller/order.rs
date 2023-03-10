use actix_web::{get, post, web, Responder, Result};
use serde::{Deserialize, Serialize};
use serde_json::{json, Value};
use std::collections::HashMap;
use std::iter::Map;

#[derive(Serialize, Deserialize)]
struct User {
    name: String,
    age: u64,
    extra: HashMap<String, Value>,
}

#[post("/user/post1")]
pub async fn post1(param: web::Json<HashMap<String, Value>>) -> Result<impl Responder> {
    let _name = param.get("name").unwrap().as_str().unwrap().to_string();
    let _age = param.get("age").unwrap().as_u64().unwrap();
    let _extra = param.get("extra").unwrap().as_object().unwrap();
    let mut map_val = HashMap::new();
    for t in _extra {
        map_val.insert(t.0.clone(), t.1.clone());
    }
    Ok(web::Json(User {
        name: _name,
        age: _age,
        extra: map_val,
    }))
}

#[get("/user/get1")]
pub async fn get1(query: web::Query<HashMap<String, Value>>) -> Result<impl Responder> {
    let _name: String = query.get("name").unwrap().as_str().unwrap().to_string();
    let _age: u64 = query.get("age").unwrap().as_str().unwrap().parse::<u64>().unwrap();
    Ok(web::Json(User {
        name: _name,
        age: _age,
        extra: Default::default(),
    }))
}

#[get("/user/{name}")]
pub async fn detail(name: web::Path<String>) -> Result<impl Responder> {
    Ok(web::Json(User {
        name: name.to_string(),
        age: 89,
        extra: Default::default(),
    }))
}
