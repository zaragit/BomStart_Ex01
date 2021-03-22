package com.bomstart.tobyspring.user.Controller;

import com.bomstart.tobyspring.user.domain.User;
import com.bomstart.tobyspring.user.service.UserService;
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

    @PostMapping("/create")
    public @ResponseBody void createUser(User user) {
        if(userService.getUser(user.getId())==null){
            userService.createUser(user);
            System.out.println("회원을 추가하였습니다.");
        } else {
            System.out.println("해당 id를 가진 회원이 있어서 회원정보를 수정합니다.");
            userService.updateUser(user);
            System.out.println("회원을 추가하였습니다.");
        }
    }

    @PutMapping("/update")
    public @ResponseBody User updateUser(User user) {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteUser(@PathVariable String id){
        if(userService.getUser(id)!=null){
            userService.deleteUser(id);
            System.out.println("회원 정보를 삭제했습니다.");
        } else {
            System.out.println("해당 id를 가진 회원이 존재하지 않습니다.");
        }
    }

}
