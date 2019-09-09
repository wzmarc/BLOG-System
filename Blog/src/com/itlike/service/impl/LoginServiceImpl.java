package com.itlike.service.impl;

import com.itlike.dao.UserDao;
import com.itlike.domian.User;
import com.itlike.service.LoginService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LoginServiceImpl implements LoginService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(User user) {
        System.out.println("用户名="+user.getUsername());
        //调用dao 查询 用户
        User resUser = userDao.getUser(user.getUsername(), user.getPassword());

        return resUser;

    }
}
