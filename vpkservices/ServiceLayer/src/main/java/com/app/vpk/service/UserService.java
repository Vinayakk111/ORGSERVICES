package com.app.vpk.service;

import com.app.vpk.entity.User;

public interface UserService {
	
	public User saveUser(User user);
	
	public User findById(Long id);

}
