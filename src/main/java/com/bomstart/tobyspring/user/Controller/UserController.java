package com.bomstart.tobyspring.user.Controller;

import com.bomstart.tobyspring.user.domain.User;
import com.bomstart.tobyspring.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/select")
    public @ResponseBody List<User> getUser() {
        return this.userService.getUsers();
    }

    @GetMapping("/select/{id}")
    public @ResponseBody User getUser(@PathVariable String id) {
        return this.userService.getUser(id);
    }

    @PutMapping("/update")
    public @ResponseBody User updateUser(User user) {
        return this.userService.updateUser(user);
    }

}
