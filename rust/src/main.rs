pub mod controller;

use actix_web::{App, HttpServer};

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| App::new()
        .service(controller::home::greet))
        .bind(("127.0.0.1", 8080))
        ?.run()
        .await
}
