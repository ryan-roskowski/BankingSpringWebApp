package com.banking.services.impl;

import org.springframework.stereotype.Service;

import com.banking.entities.User;

@Service
public class UserServiceImpl implements com.banking.services.UserService {

	@Override
	public boolean verifyUser(User u, String username, String password) {
		return (u.getUsername().equals(username) && u.getPassword().equals(password));
	}

}
