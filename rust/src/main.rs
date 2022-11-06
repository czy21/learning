use actix_web::{App, HttpServer};
use crate::home::greet;

mod home;


#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new().service(greet)
    })
        .bind(("127.0.0.1", 8080))?
        .run()
        .await
}