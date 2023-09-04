package com.maxim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.maxim.dto.User;

public class UserService {
    
    private final List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public void add(User user) {
        users.add(user);
    }

    public Optional<User> login(String name, String password) {
        return users.stream()
                        .filter(user -> user.getName().equals(name))
                        .filter(user -> user.getPassword().equals(password))
                        .findFirst();
    }
}
