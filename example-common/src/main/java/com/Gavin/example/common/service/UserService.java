package com.Gavin.example.common.service;


import com.Gavin.example.common.model.User;

/**
 * 用户服务
 *

 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法 - 获取数字，用于测试Mock
     */
    default short getNumber() {
        return 1;
    }
}
