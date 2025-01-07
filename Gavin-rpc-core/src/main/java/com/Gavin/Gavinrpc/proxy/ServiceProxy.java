package com.Gavin.Gavinrpc.proxy;

import cn.hutool.core.collection.CollUtil;

import com.Gavin.Gavinrpc.RpcConfig.RpcApplication;
import com.Gavin.Gavinrpc.RpcConfig.RpcConfig;
import com.Gavin.Gavinrpc.constant.RpcConstant;
import com.Gavin.Gavinrpc.fault.retry.RetryStrategy;
import com.Gavin.Gavinrpc.fault.retry.RetryStrategyFactory;
import com.Gavin.Gavinrpc.fault.tolerant.TolerantStrategy;
import com.Gavin.Gavinrpc.fault.tolerant.TolerantStrategyFactory;
import com.Gavin.Gavinrpc.loadbalancer.LoadBalancer;
import com.Gavin.Gavinrpc.loadbalancer.LoadBalancerFactory;
import com.Gavin.Gavinrpc.model.RpcRequest;
import com.Gavin.Gavinrpc.model.RpcResponse;
import com.Gavin.Gavinrpc.model.ServiceMetaInfo;

import com.Gavin.Gavinrpc.registry.Registry;
import com.Gavin.Gavinrpc.registry.RegistryFactory;
import com.Gavin.Gavinrpc.serializer.Serializer;
import com.Gavin.Gavinrpc.serializer.SerializerFactory;
import com.Gavin.Gavinrpc.server.tcp.VertxTcpClient;



import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理（JDK 动态代理） 消费方发起调用
 *
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            //负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 将调用方法名(请求路径)作为负载均衡参数
            Map<String,Object> requestParams = new HashMap<>();
            requestParams.put("methodName",rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams,serviceMetaInfoList);

            // rpc请求
            // 使用重试机制
            RpcResponse rpcResponse;
            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest,selectedServiceMetaInfo)
                );
            }catch (Exception e){
                //容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null,e);
            }
            return rpcResponse.getData();
        } catch (IOException e) {
            throw new RuntimeException("调用失败");
        }
    }
}
