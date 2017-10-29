package com.netease.exam.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.exam.meta.User;

@Service
@Transactional
public interface UserDao {
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "username", column = "userName"),
		@Result(property = "password", column = "password"),
		@Result(property = "nickName", column = "nickName"),
		@Result(property = "userType", column = "userType")}
	)
	@Select("select * from person where userName= #{0} and password=#{1}")
	public User getUser(String userName,String password);
}
