package com.sip.workshop.service;

import com.sip.workshop.entities.Role;
import com.sip.workshop.entities.User;
import com.sip.workshop.repository.roleRepository;
import com.sip.workshop.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class userService {
	private userRepository uR;
	private roleRepository rR;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public userService(userRepository userRepository, roleRepository roleRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.uR = userRepository;
		this.rR = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findUserByEmail(String email) {
		return uR.findByEmail(email);
	}

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = rR.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		uR.save(user);
	}
}
