package com.Gavin;


import com.Gavin.example.common.service.UserService;
import com.Gavin.Gavinrpc.LocalRegistry.LocalRegistry;
import com.Gavin.Gavinrpc.RpcConfig.RpcApplication;
import com.Gavin.Gavinrpc.server.HttpServer;
import com.Gavin.Gavinrpc.server.VertxHttpServer;
/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        //RPC框架初始化
        RpcApplication.init();

        //注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        //启动web服务
        HttpServer httpserver = new VertxHttpServer();
        httpserver.doStart(8080);
    }
}
