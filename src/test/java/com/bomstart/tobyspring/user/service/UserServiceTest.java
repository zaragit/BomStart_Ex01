package com.bomstart.tobyspring.user.service;

import com.bomstart.tobyspring.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void createTestFixture() {
        user1 = new User("TestUser1", "TestName1", "1234");
        user2 = new User("TestUser2", "TestName2", "1234");
        user3 = new User("TestUser3", "TestName3", "1234");
    }

    @AfterEach
    void clearTestFixture() {
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());
        userService.deleteUser(user3.getId());
    }

    @Test
    @DisplayName("유저 수정 테스트")
    void updateUser() {
        // given
        userService.createUser(user1);

        // when
        user1.setName("testName2");
        user1.setPassword("testPw2");
        User actual = userService.updateUser(user1);

        // then
        assertAll(
                () -> assertEquals(actual.getId(), user1.getId()),
                () -> assertEquals(actual.getName(), user1.getName()),
                () -> assertEquals(actual.getPassword(), user1.getPassword())
        );
    }

    @Test
    @DisplayName("존재하지 않는 유저 수정 테스트")
    void updateUserNotExist() {
        // given
        userService.createUser(user1);

        // when
        user1.setId(null);
        user1.setName("testName2");
        user1.setPassword("testPw2");
        User actual = userService.updateUser(user1);

        // then
        assertNull(actual);
    }

    @Test
    @DisplayName("유저 조회 테스트")
    void selectUser() {
        // given
        userService.createUser(user1);

        // when
        User actual = userService.getUser(user1.getId());

        // then
        assertNotNull(actual);
        assertAll(
                () -> assertEquals(actual.getId(), user1.getId()),
                () -> assertEquals(actual.getName(), user1.getName()),
                () -> assertEquals(actual.getPassword(), user1.getPassword())
        );
    }

    @Test
    @DisplayName("존재하지 않는 유저를 조회")
    void selectUserNotExist() {
        // given when
        User actual = userService.getUser(null);
        User actual2 = userService.getUser("!@#$%^&*(");

        // then
        assertNull(actual);
        assertNull(actual2);
    }

    @Test
    @DisplayName("유저 목록 조회 테스트")
    void selectUsers() {
        int count = userService.getUsers().size();

        assertAll(
                () -> {
                    userService.createUser(user1);
                    assertEquals(userService.getUsers().size(), count + 1);
                },
                () -> {
                    userService.createUser(user2);
                    assertEquals(userService.getUsers().size(), count + 2);
                },
                () -> {
                    userService.createUser(user3);
                    assertEquals(userService.getUsers().size(), count + 3);
                }
        );
    }
}
