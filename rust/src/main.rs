pub mod controller;

use actix_web::{App, HttpServer, web};

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .service(controller::user::controller())
    })
        .bind(("127.0.0.1", 8080))?
        .run()
        .await
}
