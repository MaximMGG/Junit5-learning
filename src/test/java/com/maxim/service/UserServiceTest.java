package com.maxim.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.maxim.dto.User;

class UserServiceTest {
    
    @Test
    void usersEmptyIfNoUserAdded() {
        UserService userService = new UserService();
        List<User> allUsers = userService.getAll();
        assertTrue(allUsers.isEmpty());
    }
}
