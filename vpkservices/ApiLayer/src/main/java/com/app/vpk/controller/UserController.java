package com.app.vpk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.app.vpk.entity.User;
import com.app.vpk.repository.UserRepository;
import com.app.vpk.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository; 

	@PostMapping("/saveUser")
	public User saveUser(@RequestBody User user) {
		User response = userService.saveUser(user);
		return response;
	}
}
