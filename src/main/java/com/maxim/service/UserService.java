package com.maxim.service;

import java.util.ArrayList;
import java.util.List;

import com.maxim.dto.User;

public class UserService {
    
    private final List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public void add(User user) {
        users.add(user);
    }
}
