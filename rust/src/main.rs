use actix_web::{App, HttpServer};

pub mod controller;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .service(controller::user::controller())
            .service(controller::order::controller())
    })
        .bind(("127.0.0.1", 8080))?
        .run()
        .await
}
