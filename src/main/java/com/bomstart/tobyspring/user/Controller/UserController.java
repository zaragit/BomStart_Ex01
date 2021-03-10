package com.bomstart.tobyspring.user.Controller;

import com.bomstart.tobyspring.user.domain.User;
import com.bomstart.tobyspring.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/select/{id}")
    public @ResponseBody User getUser(@PathVariable String id) {
        return this.userService.getUser(id);
    }

    @PostMapping("/update")
    public @ResponseBody User updateUser(User user) {
        return this.userService.updateUser(user);
    }

}
