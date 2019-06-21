package com.banking.entities;

public class User {
	private int userId;
	private String username;
	private String password;
	private String type;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	
	public User(int userId, String username, String password, String type) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.type = type;
	}
	
	public User(String username, String password, String type) {
		this.username = username;
		this.password = password;
		this.type = type;
	}


	public User(String username, String password, String type, String firstName, String lastName, String address,
			String phone) {
		this.username = username;
		this.password = password;
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}
	
	public User(int userId, String username, String password, String type, String firstName, String lastName,
			String address, String phone) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}
	
	

	public User(String firstName, String lastName, String address, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}

	public User() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	


	
	
}
