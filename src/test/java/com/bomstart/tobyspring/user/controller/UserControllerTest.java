package com.bomstart.tobyspring.user.controller;

import com.bomstart.tobyspring.user.Controller.UserController;
import com.bomstart.tobyspring.user.domain.User;
import com.bomstart.tobyspring.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    UserController controller;

    @Autowired
    UserService userService;

    @Autowired
    MockMvc mockMvc;

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
    @DisplayName("유저 조회 Rest API 테스트")
    void selectUser() throws Exception {
        userService.createUser(user1);

        mockMvc.perform(get("/user/select/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(user1.getId())))
                .andExpect(jsonPath("name", is(user1.getName())))
                .andExpect(jsonPath("password", is(user1.getPassword())));
    }

    @Test
    @DisplayName("유저 목록 조회 Rest API 테스트")
    void selectUsers() throws Exception {
        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);

        mockMvc.perform(get("/user/select"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(user1.getId())))
                .andExpect(jsonPath("$[1].id", is(user2.getId())))
                .andExpect(jsonPath("$[2].id", is(user3.getId())));
    }

    @Test
    @DisplayName("유저 수정 Rest API 테스트")
    void updateUser() throws Exception {
        userService.createUser(user1);

        String newStr = "updated";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("id", Arrays.asList(user1.getId()));
        params.put("name", Arrays.asList(newStr));
        params.put("password", Arrays.asList(newStr));

        mockMvc.perform(put("/user/update").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(newStr)))
                .andExpect(jsonPath("password", is(newStr)));
    }
}
