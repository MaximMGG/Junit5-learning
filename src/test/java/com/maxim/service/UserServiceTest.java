package com.maxim.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.maxim.dto.User;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private UserService userService;

    @BeforeAll
    static void init() {
        System.out.println("Before all: ");
    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
        userService = new UserService();
    }
    
    @Test
    void usersEmptyIfNoUserAdded() {
        System.out.println("Test 1: " + this);
        List<User> allUsers = userService.getAll();
        assertTrue(allUsers.isEmpty(), "User list should be empty");
    }

    @Test
    void usersSizeIfUserAdded() {
        System.out.println("Test 2: " + this);
        userService.add(new User());
        userService.add(new User());

        List<User> users = userService.getAll();

        assertEquals(2, users.size());
    }

    @AfterEach
    void delteFrom() {
        System.out.println("After each: " + this);
    }

    @AfterAll
    static void close() {
        System.out.println("After all: ");

    }
}
