package com.bomstart.tobyspring.user.service;

import com.bomstart.tobyspring.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @AfterEach
    @Test
    @DisplayName("테스트 끝나고 테스트 유저 삭제")
    void removeTest(){
        String userId = "testId";
        if(userService.getUser(userId)!=null){
            userService.deleteUser(userId);
        }
    }

    @Test
    @DisplayName("유저 등록 테스트")
    void createUser(){
        // given
        User user = new User();
        user.setId("testId");
        user.setName("testName");
        user.setPassword("testPw");

        // when
        userService.createUser(user);

        // then
        String expected = user.getName();
        String actual = userService.getUser(user.getId()).getName();

        System.out.println("expected : "+expected);
        System.out.println("actual   : "+actual);

        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void deleteUser(){
        // given
        int expected = userService.getUsers().size();

        User user = new User();
        user.setId("testId");
        user.setName("testName");
        user.setPassword("testPw");

        userService.createUser(user);

        // when
        userService.deleteUser(user.getId());

        // then
        int actual = userService.getUsers().size();
        System.out.println("expected " +expected);
        System.out.println("actual " +actual);
        assertEquals(actual, expected);
    }
}
