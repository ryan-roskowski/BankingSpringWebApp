package com.banking.services.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.dao.impl.AccountDaoImpl;
import com.banking.dao.impl.TransactionDaoImpl;
import com.banking.enums.*;

@Service
public class AccountServiceImpl implements com.banking.services.AccountService {
	
	@Autowired
	AccountDaoImpl accountDao;
	
	@Autowired
	TransactionDaoImpl transactionDao;

	@Override
	public DepositResult deposit(Account account, int amount) throws IOException, SQLException {
		if(accountDao.deposit(account, amount) == DepositResult.SUCCESS) {
			account.setBalance(account.getBalance()+amount);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			transactionDao.addTransaction(account, new Transaction(TransactionType.DEPOSIT, amount, dtf.format(localDate)));
			return DepositResult.SUCCESS;
		}
		return DepositResult.FAILURE;
	}

	@Override
	public WithdrawResult withdraw(Account account, int amount) throws SQLException, IOException{
		WithdrawResult res = accountDao.withdraw(account, amount);
		if(res == WithdrawResult.SUCCESS) {
			account.setBalance(account.getBalance()-amount);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			transactionDao.addTransaction(account, new Transaction(TransactionType.WITHDRAW, amount, dtf.format(localDate)));
			return WithdrawResult.SUCCESS;
		}
		else if(res == WithdrawResult.OVERDRAFT) {
			return WithdrawResult.OVERDRAFT;
		}
		return WithdrawResult.FAILURE;
	}

	

}
