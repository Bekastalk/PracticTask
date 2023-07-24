package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.UserDao;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private final Connection connection= JdbcConfig.getConnection();
    @Override
    public User findById(Long userId) {
        User user=new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *" +
                    "from users where id=?");
            preparedStatement.setLong(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User save(String username, String password, String email) {
        User user=new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(" +
                    "username, password,email) " +
                    "values(?,?,?)");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,email);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public boolean existByEmail(String email) {
        boolean exist=false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select count(*) from users where email=? ");
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                int count=resultSet.getInt(1);
                exist=count>0;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exist;
    }
}
