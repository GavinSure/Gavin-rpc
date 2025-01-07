package com.Gavin.Gavinrpc.serializer;

import com.Gavin.example.common.model.User;

import java.io.IOException;

public class TestSerializer {
    public static void main(String[] args) {
        JdkSerializer jdkSerializer = new JdkSerializer();
        User user = new User();
        try {
            byte[] serialize = jdkSerializer.serialize(user);
            System.out.println(serialize);
            User deserialize = jdkSerializer.deserialize(serialize, User.class);
            System.out.println(deserialize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
