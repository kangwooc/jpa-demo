package com.example.jpademo.repository;


import com.example.jpademo.domain.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() { // create, read, update, delete
//     how to sort .....
       List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));

       users.forEach(System.out::println);

        System.out.println("-----------");
        // get by id
        List<User> ids = userRepository.findAllById(Lists.newArrayList(1L, 3L, 5L));
        ids.forEach(System.out::println);
        System.out.println("-----------");

        // save
        User user1 = new User("james", "james@fc.com");
        User user2 = new User("tom", "tom@fc.com");

        // could save one element using .save();
        userRepository.saveAll(Lists.newArrayList(user1, user2));

        users = userRepository.findAll();

        users.forEach(System.out::println);
    }
}