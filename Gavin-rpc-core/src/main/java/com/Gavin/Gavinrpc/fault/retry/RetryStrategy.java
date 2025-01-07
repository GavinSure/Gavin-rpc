package com.Gavin.Gavinrpc.fault.retry;



import com.Gavin.Gavinrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略：接受一个具体的任务参数，可以使用Callable类代表一个任务
 */
public interface RetryStrategy {

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
