package com.netease.exam.service;

import com.netease.exam.meta.User;

public interface UserService {
	User login(String username,String password);
}
