package com.banking.entities;

import java.util.ArrayList;

import com.banking.entities.Account;
import com.banking.entities.User;

public class Customer extends User {
	private int customerId;
	private int userId;
	
	private ArrayList<Account> accounts;
	public Customer(int id, String username, String password, String type) {
		super(id, username, password, type);
	}

	public Customer(String username, String password, String type) {
		super(username, password, type);
	}
	
	public Customer(String username, String password, String type, String firstName, String lastName, String address,
			String phone) {
		super(username, password, type, firstName, lastName, address, phone);
	}
	
	public Customer(int userId,String username, String password, String type, int customerId,
			String firstName, String lastName, String address, String phone) {
		super(userId, username, password, type, firstName, lastName, address, phone);
	}

	public Customer(int customerId, int userId, String firstName, String lastName,
			String address, String phone) {
		super(firstName, lastName, address, phone);
		this.customerId = customerId;
		this.userId = userId;
	}
	
	public Customer(String firstName, String lastName, String address, String phone) {
		super(firstName, lastName, address, phone);

	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}


	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}