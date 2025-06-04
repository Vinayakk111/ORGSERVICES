package com.app.vpk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.vpk.entity.Role;
import com.app.vpk.entity.User;
import com.app.vpk.repository.RoleRepository;
import com.app.vpk.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User saveUser(User user) {

		User newuser = new User();
		newuser.setEmail(user.getEmail());
		newuser.setFullName(user.getFullName());
		User user1 = userRepository.save(newuser);
		return user1;
	}

	@Override
	public User findById(Long id) {
		Optional<User> user1 = userRepository.findById(id);
		return user1.isPresent() ? user1.get() : new User();
	}

	private String getPasswordHash(String rawPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String rawPassword = "yourPasswordHere";
		return encoder.encode(rawPassword);
	}

	public void addRoleToUser(Long userId, String roleName) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
		user.getRoles().add(role);
		userRepository.save(user);
	}
}
