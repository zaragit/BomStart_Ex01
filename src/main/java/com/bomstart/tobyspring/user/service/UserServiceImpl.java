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
        if(userDao.selectUser(user.getId())==null){
            userDao.createUser(user);
        } else {
            System.out.println("이미 존재하는 id 입니다.");
        }
    }

    @Override
    public User updateUser(User user) {
        this.userDao.updateUser(user);
        return this.userDao.selectUser(user.getId());
    }

    @Override
    public void deleteUser(String id) {
        if(getUser(id)!=null){
            userDao.deleteUser(id);
        } else {
            System.out.println("존재하지 않는 회원입니다.");
        }
    }
}
