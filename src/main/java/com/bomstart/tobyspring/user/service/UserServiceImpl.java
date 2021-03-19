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
            System.out.println("이미 존재하는 id 입니다.");
        }
        this.userDao.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        this.userDao.updateUser(user);
        return this.userDao.selectUser(user.getId());
    }

    @Override
    public void deleteUser(String id) {
        this.userDao.deleteUser(id);
    }
}
