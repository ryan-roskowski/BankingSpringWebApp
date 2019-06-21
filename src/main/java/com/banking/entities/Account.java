package com.banking.entities;

public class Account {
	private int Id;
	private int accountNumber;
	private int customerId;
	private long balance;
	private String type;
	
	
	public Account(int id, int accountNumber, int customerId, long balance, String type) {
		Id = id;
		this.accountNumber = accountNumber;
		this.customerId = customerId;
		this.balance = balance;
		this.type = type;
	}
	
	
	public Account(int accountNumber, int customerId, long balance, String type) {
		this.accountNumber = accountNumber;
		this.customerId = customerId;
		this.balance = balance;
		this.type = type;
	}


	public Account(int accountNumber, long balance, String type) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.type = type;
	}
	
	public Account() {
		
	}


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	
	
}
