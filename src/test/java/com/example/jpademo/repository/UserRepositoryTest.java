package com.example.jpademo.repository;


import com.example.jpademo.domain.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    // sustain session when uses id => lazy loading
    @Transactional
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
        // if use .save(), id is being 1 so it occurs error
        userRepository.saveAll(Lists.newArrayList(user1, user2));

        users = userRepository.findAll();

        users.forEach(System.out::println);
        System.out.println("-----------");

        // https://stackoverflow.com/questions/24482117/when-use-getone-and-findone-methods-spring-data-jpa
        // getOne() => entity support lazy patch
        // findOne() => Optional entity eager loading.
        User user3 = userRepository.getOne(1L);
        System.out.println(user3);
        System.out.println("-----------");

        // relational
        // findOne() => Optional entity eager loading.
       User user4 = userRepository.findById(1L).orElse(null);
       System.out.println(user4);

       System.out.println("-----------");

       // flush
        // same with saveAndFlush
       userRepository.save(new User("new martin", "newmartin@fc.com"));
       userRepository.flush();
       userRepository.findAll().forEach(System.out::println);
        System.out.println("-----------");

        // count
        long count = userRepository.count();
        System.out.println(count);
        System.out.println("-----------");

        // exists
        // Count query string 참조
        boolean exists = userRepository.existsById(1L);
        System.out.println(exists);
        System.out.println("-----------");

        // delete
        // cause problem when adding null
        // so need to use ElseThrow to throw exception
        userRepository.delete(userRepository.findById(8L).orElseThrow(RuntimeException::new));
        userRepository.findAll().forEach(System.out::println);
        System.out.println("-----------");

        // deleteAll 각각 id가 존재하는지체크 => 성능 이슈
        userRepository.deleteById(7L);
        userRepository.findAll().forEach(System.out::println);
        System.out.println("-----------");

        /*
         deleteInBatch()
         delete
            from
                user
            where
                id=?
                or id=?
                or query
         deleteAllInBatch() => delete all in batch
         userRepository.deleteInBatch(userRepository.findAllById(Lists.newArrayList(1L, 3L)));
         userRepository.findAll().forEach(System.out::println);
        */

        // Paging
        Page<User> pages = userRepository.findAll(PageRequest.of(1, 3));
        // Page 2 of 2 containing com.example.jpademo.domain.User instances
        System.out.println("page: "+ pages);
        // totalElements: 6
        System.out.println("totalElements: " + pages.getTotalElements());
        // totalPage
        System.out.println("totalPages: " + pages.getTotalPages());
        // numbers of element
        System.out.println("number of elements: " + pages.getNumberOfElements());
        // sort
        System.out.println("sort: " + pages.getSort());
        // size
        System.out.println("size: " + pages.getSize());

        pages.getContent().forEach(System.out::println);
        System.out.println("-----------");
        /*
        Query 생성
         QueryByExample => 쿼리가 복잡해지면 다른 것을 씀 QueryDSL
         Entity 를 Example 로 만들고 examplematcher
         select
                user0_.id as id1_0_,
                user0_.created_at as created_2_0_,
                user0_.email as email3_0_,
                user0_.name as name4_0_,
                user0_.updated_at as updated_5_0_
            from
                user user0_
            where
                user0_.email like ? escape ?
        */
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name") // user 조회를 무시 exact match
                // endWith()
                // contains()
                .withMatcher("email", endsWith());
/*
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("email", endsWith());
                => name = "ma" and email "fc.com" exact matcher
*/
        Example<User> example = Example.of(new User("ma", "fc.com"), matcher);
        userRepository.findAll(example).forEach(System.out::println);

    }
}