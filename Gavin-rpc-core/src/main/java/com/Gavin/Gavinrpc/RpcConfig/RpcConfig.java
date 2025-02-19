package com.Gavin.Gavinrpc.RpcConfig;


import com.Gavin.Gavinrpc.fault.retry.RetryStrategyKeys;
import com.Gavin.Gavinrpc.fault.tolerant.TolerantStrategyKeys;
import com.Gavin.Gavinrpc.loadbalancer.LoadBalancerKeys;
import com.Gavin.Gavinrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架配置
 */
@Data
public class RpcConfig {

    /**
     * 容错策略
     */
    private String tolerantStrategy= TolerantStrategyKeys.FAIL_FAST;

    /**
     * 重试策略
     */
    private String retryStrategy= RetryStrategyKeys.NO;

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 名称
     */
    private String name = "Gavin-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";
    
    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

}
