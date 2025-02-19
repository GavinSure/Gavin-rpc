package com.Gavin;


import com.Gavin.Gavinrpc.bootstrap.ProviderBootstrap;
import com.Gavin.Gavinrpc.model.ServiceRegisterInfo;
import com.Gavin.example.common.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务提供者示例
 *

 */
public class ProviderExample {

    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserService> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
