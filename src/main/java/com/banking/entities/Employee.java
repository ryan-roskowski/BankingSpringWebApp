package com.banking.entities;

import com.banking.entities.User;

public class Employee extends User {
	private int employeeId;
	private int userId;
	private String employeeType;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(int userId, String username, String password, String type) {
		super(userId, username, password, type);
		// TODO Auto-generated constructor stub
	}
	public Employee(String username, String password, String type) {
		super(username, password, type);
		// TODO Auto-generated constructor stub
	}
	
	public Employee(String username, String password, String type, String firstName, String lastName, String address,
			String phone, String employeeType) {
		super(username, password, type, firstName, lastName, address, phone);
		this.employeeType = employeeType;
	}
	public Employee(int userId, String username, String password, String type, int employeeId,
			String firstName, String lastName, String address, String phone, String employeeType) {
		super(userId, username, password, type, firstName, lastName, address, phone);
		this.employeeId = employeeId;
		this.userId = userId;
		this.employeeType = employeeType;
	}
	
	public Employee(int employeeId, int userId, String firstName, String lastName, String address, String phone, String employeeType) {
		super(firstName, lastName, address, phone);
		this.employeeId = employeeId;
		this.userId = userId;
		this.employeeType = employeeType;
	}
	public Employee(String firstName, String lastName, String address, String phone, String employeeType) {
		super(firstName, lastName, address, phone);
		this.employeeType = employeeType;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	 
	
}
