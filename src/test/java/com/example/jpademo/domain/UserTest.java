package com.example.jpademo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void test() {
        User user = new User();
        user.setEmail("kw@gmail.com");
        user.setName("name");
        // if there's no toString(), printed hashcode
        System.out.println(">>>" + user);

//        User.builder().name()
    }
}