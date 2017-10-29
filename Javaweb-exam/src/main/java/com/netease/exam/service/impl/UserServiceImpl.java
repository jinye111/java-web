package com.netease.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.exam.dao.UserDao;
import com.netease.exam.meta.User;
import com.netease.exam.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
    public UserDao userDao;
	
	@Override
	public User login(String userName,String password) {
        return userDao.getUser(userName, password);
    }
}
