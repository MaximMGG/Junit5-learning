package com.maxim.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import lombok.SneakyThrows;

public class UserDao {
    
    @SneakyThrows
    public boolean delete(Integer userId) {
        try (Connection connection = DriverManager.getConnection("url", "username", "passord")) {
        }
       return true;
    }
}