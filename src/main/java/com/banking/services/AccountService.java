package com.banking.services;

import java.io.IOException;

import java.sql.SQLException;

import com.banking.entities.Account;
import com.banking.enums.*;

public interface AccountService {
	public DepositResult deposit(Account account, int amount) throws IOException, SQLException;
	public WithdrawResult withdraw(Account account, int amount) throws IOException, SQLException;
}
