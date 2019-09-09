package com.itlike.dao;

import com.itlike.domian.User;

public interface UserDao {
    public User getUser(String username, String password);
}
