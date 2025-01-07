package com.Gavin;


import com.Gavin.Gavinrpc.bootstrap.ConsumerBootstrap;
import com.Gavin.example.common.model.User;
import com.Gavin.example.common.service.UserService;
import com.Gavin.Gavinrpc.proxy.ServiceProxyFactory;

/**
 * 服务消费者示例
 *
 */
public class ConsumerExample {

    public static void main(String[] args) {
        //服务消费者初始化
        ConsumerBootstrap.init();
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("Gavin");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }


        //用于测试Mock的
        //long number = userService.getNumber();
        //System.out.println(number);
    }
}
