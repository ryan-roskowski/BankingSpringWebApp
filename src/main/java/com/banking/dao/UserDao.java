package com.banking.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.banking.entities.User;


public interface UserDao {
	public User getUser(String username) throws SQLException;
	public void addUser(String username, String password, String type) throws IOException, SQLException, Exception;
	
	
}
