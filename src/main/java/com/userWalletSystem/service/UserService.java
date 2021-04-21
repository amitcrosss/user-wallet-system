package com.userWalletSystem.service;

import com.userWalletSystem.entity.User;


public interface UserService {

	public User saveUserInfo(User user) throws Exception;
	
	public User userLoginService(String email, String password) throws Exception;
	
	public User isValidUser(Integer userId);	
}
