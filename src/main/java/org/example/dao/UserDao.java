package org.example.dao;

import org.example.model.User;

public interface UserDao {
    User findById(Long userId);

    User save(String username, String password, String email);

    boolean existByEmail(String email);
}
