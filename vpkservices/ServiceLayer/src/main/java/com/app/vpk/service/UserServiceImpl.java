package com.app.vpk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.vpk.entity.User;
import com.app.vpk.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public User saveUser(User user) {

		User newuser = new User();
		newuser.setEmail(user.getEmail());
		newuser.setName(user.getName());
		User user1 = userRepo.save(newuser);
		return user1;
	}
	
	@Override
	public User findById(Long id) {
		Optional<User> user1 = userRepo.findById(id);
		return user1.isPresent() ?user1.get():new User(); 
	}

}
