package com.Gavin.Gavinrpc.loadbalancer;

import com.Gavin.Gavinrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 一致性哈希负载均衡器
 */
public class ConsistentHashLoadBalancer implements LoadBalancer {

    /**
     * 一致性Hash环，存放虚拟节点
     * 1.根据 requestParams 对象计算 Hash 值，这里只是简单地调用了对象的 hashCode 方法，可以根据需求实现自己的 Hash 算法。
     * 2.每次调用负载均衡器时，都会重新构造 Hash 环，这是为了能够即时处理节点的变化。
     */
    private final TreeMap<Integer,ServiceMetaInfo> virtualNodes = new TreeMap<>();
    /**
     * 虚拟节点数
     */
    private static final int VIRTUAL_NODE_NUM = 100;

    /**
     *
     * @param requestParams       请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (serviceMetaInfoList.isEmpty())
            return null;

        //构建虚拟节点环
        for (ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                int hash = getHash(serviceMetaInfo.getServiceAddress() + "#" + i);
                virtualNodes.put(hash,serviceMetaInfo);
            }
        }

        //获取调用请求的hash值
        int hash = getHash(requestParams);

        //选择最接近且大于等于调用请求hash值的虚拟节点
        Map.Entry<Integer,ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if (entry == null){
            // 如果没有就返回首部的节点
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    /**
     * Hash算法，可自行实现
     */
    private int getHash(Object key) {
        return key.hashCode();
    }
}
