package com.banking.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import com.banking.entities.Account;
import com.banking.entities.Customer;
import com.banking.entities.DBProperties;
import com.banking.enums.DepositResult;
import com.banking.enums.WithdrawResult;
import com.banking.data.Database;

@Component
public class AccountDaoImpl implements com.banking.dao.AccountDao {
	
	@Autowired
	private DBProperties properties;
	
	@Autowired
	private Database data;
	
	@Autowired JdbcTemplate jdbcTemplate;
	
	private boolean useDatabase;
	public AccountDaoImpl(DBProperties properties, Database data) throws ClassNotFoundException, IOException {
		if(properties.getDatasource().equals("database")) {
			this.useDatabase = true;
		}
		else if(properties.getDatasource().equals("file")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(properties.getAccountData()));
				String line;
				String[] accountData;
				while((line = reader.readLine()) != null) {
					accountData = line.split(":");
					data.getAccountList().put(Integer.parseInt(accountData[0]), new Account(Integer.parseInt(accountData[0]), Integer.parseInt(accountData[1]), Integer.parseInt(accountData[2]), Long.parseLong(accountData[3]), accountData[4]));
				}			
				reader.close();
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("Error, user data file not found.");
			} catch(IOException e) {
				throw new IOException("Error reading user data file.");
			}
			
		}
	}
	@Override
	public ArrayList<Account> getAccounts(Customer customer) throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		if(!isUseDatabase()) {
			for(Account a : data.getAccountList().values()) {
				if(customer.getCustomerId() == a.getCustomerId()) {
					accounts.add(a);
				}
			}
			return accounts;
		}
		accounts.addAll(jdbcTemplate.query("Select * from BANKING_APPLICATION_ACCOUNTS where CUSTOMER_ID = "+customer.getCustomerId(), new RowMapper<Account>() {
			@Override
			public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Account(Integer.parseInt(rs.getString("ID")), Integer.parseInt(rs.getString("ACCOUNT_NUMBER")), Integer.parseInt(rs.getString("CUSTOMER_ID")), Long.parseLong(rs.getString("BALANCE")), rs.getString("TYPE"));
			}
				
		}));
			
		return accounts;
	}
	public boolean isUseDatabase() {
		return useDatabase;
	}
	public void setUseDatabase(boolean useDatabase) {
		this.useDatabase = useDatabase;
	}
	@Override
	public void addAccount(Customer customer, String type) throws SQLException, IOException {
		if(!isUseDatabase()) {
				int uniqueID;
				int uniqueAccNumber;
				boolean unique = true;
				while(true) {
					uniqueID = (int) (Math.random() * 1000000);
					uniqueAccNumber = (int) (Math.random() * 100000000);
					for(Account a  : data.getAccountList().values()) {
						if(a.getId() == uniqueID || a.getAccountNumber() == uniqueAccNumber) {
							unique = false;
							break;
						}
					}
					if(unique)
						break;
				}
			data.getAccountList().put(uniqueID, new Account(uniqueID, uniqueAccNumber, customer.getCustomerId(), 0, type));
			if(properties.getDatasource().equals("file")) {
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getAccountData(), true));
					writer.write(uniqueID+":"+uniqueAccNumber+":"+customer.getCustomerId()+":"+0+":"+type);
					writer.newLine();
					writer.close();
				} catch(IOException e) {
					throw new IOException("Error writing new account to data file.");
				}
			}
		}
		else {
			jdbcTemplate.execute("Insert into BANKING_APPLICATION_ACCOUNTS (ID, ACCOUNT_NUMBER, TYPE, CUSTOMER_ID, BALANCE) VALUES (BANKING_APPLICATION_ACCOUNTS_PKSEQ.nextval, BANKING_APPLICATION_ACCOUNTS_ACCOUNT_NUMBER.nextval , '"+type+"', "+customer.getCustomerId()+", 0)");
		}
		
	}
	@Override
	public DepositResult deposit(Account account, int amount) throws IOException, SQLException {
		if(!isUseDatabase() && properties.getDatasource().equals("file")) {
			try {
				String line;
				ArrayList<String> lines = new ArrayList<String>();
				BufferedReader reader = new BufferedReader(new FileReader(properties.getAccountData()));
				while((line = reader.readLine()) != null) {
					lines.add(line);
				}
				reader.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getAccountData()));
				String[] segments;
				for(String l : lines) {
					segments = l.split(":");
					if(segments[0].equals(Integer.toString(account.getId()))) {
						writer.write(account.getId()+":"+account.getAccountNumber()+":"+account.getCustomerId()+":"+(account.getBalance()+amount)+":"+account.getType());
						writer.newLine();
					}
					else {
						writer.write(l);
					}
				}
				writer.close();
				return DepositResult.SUCCESS;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw new FileNotFoundException("Error, account data file not found on deposit.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException("Error reading/writing account data file on deposit.");
			}
		}
		else if(properties.getDatasource().equals("database")) {
			jdbcTemplate.execute("UPDATE BANKING_APPLICATION_ACCOUNTS SET BALANCE = "+(account.getBalance()+amount)+" WHERE ID = "+account.getId());
		}
		return DepositResult.SUCCESS;
	}
	@Override
	public WithdrawResult withdraw(Account account, int amount) throws SQLException, IOException {
		if(account.getBalance()-amount < 0)
			return WithdrawResult.OVERDRAFT;
		if(!isUseDatabase() && properties.getDatasource().equals("file")) {
			try {
				String line;
				ArrayList<String> lines = new ArrayList<String>();
				BufferedReader reader = new BufferedReader(new FileReader(properties.getAccountData()));
				while((line = reader.readLine()) != null) {
					lines.add(line);
				}
				reader.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getAccountData()));
				String[] segments;
				for(String l : lines) {
					segments = l.split(":");
					if(segments[0].equals(Integer.toString(account.getId()))) {
						writer.write(account.getId()+":"+account.getAccountNumber()+":"+account.getCustomerId()+":"+(account.getBalance()-amount)+":"+account.getType());
						writer.newLine();
					}
					else {
						writer.write(l);
					}
				}
				writer.close();
				return WithdrawResult.SUCCESS;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw new FileNotFoundException("Error, account data file not found on withdraw.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException("Error reading/writing account data file on withdraw.");
			}
		}
		else if (properties.getDatasource().equals("database")){
			jdbcTemplate.execute("UPDATE BANKING_APPLICATION_ACCOUNTS SET BALANCE = "+(account.getBalance()-amount)+" WHERE ID = "+account.getId());
		}
		return WithdrawResult.SUCCESS;
	}
}
