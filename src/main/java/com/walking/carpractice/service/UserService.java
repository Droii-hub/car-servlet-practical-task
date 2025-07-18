package com.walking.carpractice.service;

import com.walking.carpractice.model.User;
import com.walking.carpractice.repository.UserRepository;

import javax.sql.DataSource;

public class UserService {
    private static UserService instance;
    private UserService(DataSource dataSource){
        this.repository=new UserRepository(dataSource);
    }

    public static  UserService getInstance(DataSource dataSource){
        if (instance==null)
            instance=new UserService(dataSource);
        return instance;
    }
    private final UserRepository repository;

    public void createUser(User user){
        repository.create(user);
    }

    public void updatePassword(User user){
        repository.update(user);
    }

    public String readPasswordByEmail(String email){
        return repository.passwordByEmail(email);
    }

    public void deleteUser(String email){
        repository.delete(email);
    }
}
