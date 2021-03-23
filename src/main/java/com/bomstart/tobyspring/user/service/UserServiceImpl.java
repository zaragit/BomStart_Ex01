package com.bomstart.tobyspring.user.service;

import com.bomstart.tobyspring.user.dao.UserDao;
import com.bomstart.tobyspring.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserDao userDao;

    public UserServiceImpl (UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(String id) {
        return this.userDao.selectUser(id);
    }

    @Override
    public List<User> getUsers() {
        return this.userDao.selectUsers();
    }

    @Override
    public void createUser(User user) {
        if(userDao.selectUser(user.getId())!=null){
            System.out.println("이미 등록된 회원입니다.");
        } else {
            System.out.println("회원 등록을 완료하였습니다.");
            this.userDao.createUser(user);
        }
    }

    @Override
    public User updateUser(User user) {
        this.userDao.updateUser(user);
        return this.userDao.selectUser(user.getId());
    }

    @Override
    public void deleteUser(String id) {
        if(userDao.selectUser(id)==null){
            System.out.println("등록되지 않은 회원입니다.");
        } else {
            System.out.println("회원을 삭제했습니다.");
            this.userDao.deleteUser(id);
        }
    }
}
