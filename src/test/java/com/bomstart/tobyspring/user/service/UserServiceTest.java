package com.bomstart.tobyspring.user.service;

import com.bomstart.tobyspring.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void createUser(){
        // given
        User expected = user1;

        // when
        userService.createUser(expected);

        // then
        User actual = userService.getUser(expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("중복 회원 생성")
    void createDuplicateUser() {
        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            // given
            User expected = user1;

            // when
            userService.createUser(expected);
            userService.createUser(expected);
        });
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
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            // given
            userService.createUser(user1);

            // when
            User wrongUser = new User(null, "!@#$%^&*(", "!@#$%^&*(");
            userService.updateUser(wrongUser);
        });
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
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            userService.getUser(null);
        });
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

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteUser(){
        // given
        userService.createUser(user1);
        assertEquals(1, userService.getUsers().size());

        // when
        userService.deleteUser(user1.getId());

        // then
        assertEquals(0, userService.getUsers().size());
    }

}
