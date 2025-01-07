package com.gavin.examplespringbootconsumer.service.impl;


import com.Gavin.example.common.model.User;
import com.Gavin.example.common.service.UserService;
import com.gavin.Gavinrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("gavin");
        User resultUser = userService.getUser(user);
        System.out.println("调用远程服务："+resultUser.getName());
    }

}
