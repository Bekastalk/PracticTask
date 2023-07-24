package org.example.services.impl;

import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.model.User;
import org.example.services.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao=new UserDaoImpl();
    @Override
    public User findById(Long userId) {

        return userDao.findById(userId);
    }

    @Override
    public User save(String username, String password, String email) {
        return userDao.save(username,password,email);
    }

    @Override
    public boolean existByEmail(String email) {
        return userDao.existByEmail(email);
    }
}
