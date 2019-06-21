package com.banking.enums;

public enum UserAction {
	CREATE_CUSTOMER, CREATE_ACCOUNT_FOR_CUSTOMER, MANAGER_1("Performed Manager Option 1"), 
	MANAGER_2("Performed Manager Option 2"), TELLER_1("Performed Teller Option 1"), 
	TELLER_2("Performed Teller Option 2"), LOGOUT, INVALID("Error, Invalid Option"),
	VIEW_BALANCE, DEPOSIT, WITHDRAW, VIEW_TRANSACTIONS;
	
	private String message;

	private UserAction(String message) {
		this.message = message;
	}
	
	private UserAction() {
		this.message = "";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
