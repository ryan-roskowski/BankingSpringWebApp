package com.banking.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banking.entities.Account;
import com.banking.entities.Customer;
import com.banking.entities.User;
import com.banking.dao.impl.AccountDaoImpl;
import com.banking.dao.impl.TransactionDaoImpl;
import com.banking.services.impl.AccountServiceImpl;

@Component
public class AccountController {
	
	@Autowired
	AccountDaoImpl accountDao;
	
	@Autowired
	AccountServiceImpl accountService;
	
	@Autowired
	TransactionDaoImpl transactionDao;
	
	
	public ArrayList<Account> getAccounts(Customer customer) throws SQLException {
		return accountDao.getAccounts(customer);
	}
}
