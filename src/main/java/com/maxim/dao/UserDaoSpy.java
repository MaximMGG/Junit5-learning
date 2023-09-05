package com.maxim.dao;

import java.util.HashMap;
import java.util.Map;

public class UserDaoSpy extends UserDao {

    private UserDao userDao;
    private Map<Integer, Boolean> answer = new HashMap<>();

    public UserDaoSpy(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean delete(Integer userId) {
        return answer.getOrDefault(userId, userDao.delete(userId));
    }


    
}
