pub mod controller;

use actix_web::{App, HttpServer};

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .service(controller::user::post1)
            .service(controller::user::get1)
            .service(controller::user::detail)
    })
    .bind(("127.0.0.1", 8080))?
    .run()
    .await
}
