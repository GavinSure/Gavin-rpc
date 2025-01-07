package com.Gavin.Gavinrpc.bootstrap;


import com.Gavin.Gavinrpc.LocalRegistry.LocalRegistry;
import com.Gavin.Gavinrpc.RpcConfig.RegistryConfig;
import com.Gavin.Gavinrpc.RpcConfig.RpcApplication;
import com.Gavin.Gavinrpc.RpcConfig.RpcConfig;
import com.Gavin.Gavinrpc.model.ServiceMetaInfo;
import com.Gavin.Gavinrpc.model.ServiceRegisterInfo;
import com.Gavin.Gavinrpc.registry.Registry;
import com.Gavin.Gavinrpc.registry.RegistryFactory;
import com.Gavin.Gavinrpc.server.tcp.VertxTcpServer;

import java.util.List;

public class ProviderBootstrap {
    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        //框架初始化(配置和注册中心)
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName,serviceRegisterInfo.getImplClass());

            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            }catch (Exception e){
                throw new RuntimeException(serviceName + "服务注册失败",e);
            }
        }

        //启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
