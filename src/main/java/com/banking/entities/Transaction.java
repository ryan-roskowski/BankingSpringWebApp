package com.banking.entities;

import com.banking.enums.TransactionType;
public class Transaction {
	int id;
	int accountId;
	int customerId;
	private TransactionType type;
	private int amount;
	private String date;
	
	
	
	public Transaction(int id, int accountId, int customerId, TransactionType type, int amount, String date) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.customerId = customerId;
		this.type = type;
		this.amount = amount;
		this.date = date;
	}

	

	public Transaction(TransactionType type, int amount, String date) {
		super();
		this.type = type;
		this.amount = amount;
		this.date = date;
	}

	public Transaction(TransactionType type, int amount) {
		super();
		this.type = type;
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	@Override
	public String toString() {
		return "[Date: "+date +", Type: "+type+", Amount: "+amount+"]";
	}
	
	
	
	
	
}
