package com.Gavin.Gavinrpc.fault.retry;


/**
 * 用于支持配置和扩展重试策略
 * 重试策略键名常量
 *
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

}
