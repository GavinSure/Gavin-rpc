package com.gavin.Gavinrpc.springboot.starter.annotation;



import com.Gavin.Gavinrpc.constant.RpcConstant;
import com.Gavin.Gavinrpc.fault.retry.RetryStrategyKeys;
import com.Gavin.Gavinrpc.fault.tolerant.TolerantStrategyKeys;
import com.Gavin.Gavinrpc.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务消费者注解（用于注入服务），在需要注入服务代理对象的属性上使用，类似 Spring 中的 @Resource 注解。
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcReference {

    /**
     * 服务接口类，调用的服务是哪种
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     */
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    String retryStrategy() default RetryStrategyKeys.NO;

    /**
     * 容错策略
     */
    String tolerantStrategy() default TolerantStrategyKeys.FAIL_FAST;

    /**
     * 模拟调用
     */
    boolean mock() default false;

}
