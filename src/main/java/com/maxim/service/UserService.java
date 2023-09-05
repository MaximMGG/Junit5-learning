package com.maxim.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.maxim.dao.UserDao;
import com.maxim.dto.User;

public class UserService {
    
    private final List<User> users = new ArrayList<>();
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean delete(Integer userId) {
       return userDao.delete(userId);
    }


    public List<User> getAll() {
        return users;
    }


    public void add(User... users) {
        this.users.addAll(Arrays.asList(users));
    }

    public Optional<User> login(String name, String password) {
        if (name == null || password == null) {
            throw new IllegalArgumentException("username or password is null");
        }
        return users.stream()
                        .filter(user -> user.getName().equals(name))
                        .filter(user -> user.getPassword().equals(password))
                        .findFirst();
    }

    public Map<Integer, User> getAllConvertedById() {
        return users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
