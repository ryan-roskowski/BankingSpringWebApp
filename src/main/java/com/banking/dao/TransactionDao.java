package com.banking.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.banking.entities.Account;
import com.banking.entities.Customer;
import com.banking.entities.Transaction;

public interface TransactionDao {
	public ArrayList<Transaction> getTransactions(Customer customer, Account account) throws SQLException, IOException;
	public void addTransaction(Account account, Transaction transaction) throws SQLException, IOException;
}
