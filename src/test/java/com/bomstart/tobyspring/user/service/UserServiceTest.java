package com.bomstart.tobyspring.user.service;

import com.bomstart.tobyspring.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void createTestFixture(){
        user1 = new User("TestUser1", "TestName1", "1234");
        user2 = new User("TestUser2", "TestName2", "1234");
        user3 = new User("TestUser3", "TestName3", "1234");
    }

    @AfterEach
    void clearTestFixture(){
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());
        userService.deleteUser(user3.getId());
    }

    @Test
    @DisplayName("회원 등록 테스트")
    void createUser() {
        // when
        User expected = user1;
        userService.createUser(expected);
        User actual = userService.getUser(expected.getId());

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteUser(){
        // when
        userService.createUser(user1);
        userService.deleteUser(user1.getId());

        // then
        User actual = userService.getUser(user1.getId());
        assertThat(actual).isNull();
    }
}
