package com.banking.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.banking.entities.Account;
import com.banking.entities.Customer;
import com.banking.entities.User;
import com.banking.enums.DepositResult;
import com.banking.enums.WithdrawResult;
import com.banking.enums.*;

public interface AccountDao {
	public ArrayList<Account> getAccounts(Customer customer) throws SQLException;
	public void addAccount(Customer customer, String type) throws SQLException, IOException;
	public DepositResult deposit(Account account, int amount) throws IOException, SQLException;
	public WithdrawResult withdraw(Account account, int amount) throws IOException, SQLException;
}
