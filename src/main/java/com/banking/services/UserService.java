package com.banking.services;

import com.banking.entities.User;

public interface UserService {
	public boolean verifyUser(User u, String username, String password);
}
