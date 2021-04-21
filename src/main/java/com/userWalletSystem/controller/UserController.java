package com.userWalletSystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.userWalletSystem.entity.User;
import com.userWalletSystem.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "api/signup")
	public ResponseEntity signUp(@RequestBody User user) {
		try {
			User userCreated = userService.saveUserInfo(user);
			// response.setStatus("200");
			// response.setDescription("Sign Up successful!");
			// response.setData(newCustomer);
			String resp = "Sign Up complete";
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (Exception e) {
			// response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
			// response.setDescription(e.getMessage());
			String resp = e.getMessage();
			return new ResponseEntity<>(resp, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping(value = "api/signin")
	public ResponseEntity signIn(@RequestBody Map<String, String> body) {
		String email = body.get("email");
		String pass = body.get("password");
		try {
			User userCreated = userService.userLoginService(email, pass);
			// response.setStatus("200");
			// response.setDescription("Sign Up successful!");
			// response.setData(newCustomer);
			String resp = "Successful";
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (Exception e) {
			// response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
			// response.setDescription(e.getMessage());
			String resp = e.getMessage();
			return new ResponseEntity<>(resp, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
