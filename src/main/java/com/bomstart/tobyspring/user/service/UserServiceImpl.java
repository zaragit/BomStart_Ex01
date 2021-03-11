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
    public User updateUser(User user) {
        this.userDao.updateUser(user);
        return this.userDao.selectUser(user.getId());
    }
}
