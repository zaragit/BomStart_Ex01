package com.bomstart.tobyspring.user.Controller;

import com.bomstart.tobyspring.user.domain.User;
import com.bomstart.tobyspring.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("유저 등록 테스트")
    void createUser() {
        // given
        String expected = "TestUser";
        User user = new User();
        user.setId(expected);
        user.setName("TestName");
        user.setPassword("123456789");

        // when
        userController.createUser(user);

        // then
        String actual = userService.getUser(expected).getId();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void deleteUser(){
        // given
        String expected = "TestUser";
        User user = new User();
        user.setId(expected);
        user.setName("TestName");
        user.setPassword("123456789");

        userController.createUser(user);

        // when
        userController.deleteUser(user.getId());

        // then
        User actual = userController.getUser(user.getId());
        assertThat(actual).isNull();
    }

    @ParameterizedTest(name = "존재하지 않는 회원에 대한 삭제요청 테스트")
    @ValueSource(strings={"noUser", "null"})
    void deleteUSerNotExist(String id){
        // given
        User user = new User();
        if(id.equals("null")){
            id = null;
        }
        user.setId(id);
        user.setName("TestName");
        user.setPassword("123456789");

        // when
        userController.deleteUser(id);

        // then
        User actual = userService.getUser(user.getId());
        assertThat(actual).isNull();
    }

}
