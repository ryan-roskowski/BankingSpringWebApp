package com.banking.entities;

import com.banking.entities.Account;

public class CheckingAccount extends Account {
	
	private final int withdrawLimit = 10000;
	private final double interest = 0.0;
	public int getWithdrawLimit() {
		return withdrawLimit;
	}
	public double getInterest() {
		return interest;
	}
	
	
	
}
