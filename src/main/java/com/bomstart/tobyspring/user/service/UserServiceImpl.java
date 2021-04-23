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
        this.userDao.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        User savedUser = this.userDao.selectUser(user.getId());

        if (!user.getName().equals("")) {
            savedUser.setName(user.getName());
        }

        if (!user.getPassword().equals("")) {
            savedUser.setPassword(user.getPassword());
        }

        this.userDao.updateUser(savedUser);

        return this.userDao.selectUser(user.getId());
    }

    @Override
    public void deleteUser(String id) {
        this.userDao.deleteUser(id);
    }
}
