package com.banking.entities;

import com.banking.entities.Account;

public class SavingsAccount extends Account {

	private final int withdrawLimit = 500;
	private final double interest = 0.02;

	public int getWithdrawLimit() {
		return withdrawLimit;
	}
	public double getInterest() {
		return interest;
	}

}
