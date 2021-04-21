package com.userWalletSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userWalletSystem.entity.User;
import com.userWalletSystem.entity.Wallet;
import com.userWalletSystem.repository.UserRepository;
import com.userWalletSystem.repository.WalletRepsitory;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	WalletRepsitory walletRepsitory;

	@Override
	public User saveUserInfo(User user) throws Exception {

		String email = user.getEmail();
		Optional<User> existingUser = userRepository.findUserByEmail(email);

		if (existingUser.isPresent()) {
			throw new Exception("user already exist with same email id");
		}
		// if user doesn't exist create user and wallet for him
		User createdUser = userRepository.save(user);

		Wallet wallet = new Wallet();
		wallet.setUser(createdUser);
		wallet.setBalance(0);
		walletRepsitory.save(wallet);

		return createdUser;
	}

	@Override
	public User isValidUser(Integer userId) {
		Optional<User> existingUser = userRepository.findById(userId);
		if(existingUser.isPresent())
			return existingUser.get();
		return null;
	}

	@Override
	public User userLoginService(String email, String password) throws Exception {

		Optional<User> existingUser = userRepository.findUserByEmailAndPassword(email, password);
		if(!existingUser.isPresent()) {
			existingUser = userRepository.findUserByEmail(email);
			if(!existingUser.isPresent())
				throw new Exception("User doesn't exists, Kindly complete registeration to continue");
			else
				throw new Exception("Incorrect email or password! Try Again");
		}
		
		return existingUser.get();
	}

}
