package com.Gavin.Gavinrpc.server;

import io.vertx.core.Vertx;


/*
* Vertx作为web服务器，让服务提供者提供可远程调用的服务，基于Vert.x实现的web服务器VertxHttpServer,能够监听指定端口并处理请求
*
* */
public class VertxHttpServer implements HttpServer {

    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        //监听端口并处理请求
        server.requestHandler(new HttpServerHandler());
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            }else {
                System.err.println("Failed to start server: " + result.cause());
            }
        });
    }
}
