package com.banking.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banking.entities.Account;
import com.banking.entities.Customer;
import com.banking.entities.Transaction;
import com.banking.entities.Properties;
import com.banking.enums.TransactionType;
import com.banking.data.Database;

@Component
public class TransactionDaoImpl implements com.banking.dao.TransactionDao {
	
	@Autowired
	private Database data;
	
	@Autowired
	private Properties properties;
	
	private boolean useDatabase;
	
	public TransactionDaoImpl(Properties properties, Database data) throws ClassNotFoundException, IOException {
		if(properties.getProperties().get("data-source").equals("database")) {
			this.useDatabase = true;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				throw new ClassNotFoundException("oracle.jdbc.driver.OracleDriver class not found.");
			}
		}
		else if(properties.getProperties().get("data-source").equals("file")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(properties.getProperties().get("transactionData")));
				String line;
				String[] transactionData;
				while((line = reader.readLine()) != null) {
					transactionData = line.split(":");
					TransactionType type;
					if(transactionData[2].equals("Deposit")) {
						type = TransactionType.DEPOSIT;
					}
					else {
						type = TransactionType.WITHDRAW;
					}
					data.getTransactionList().put(Integer.parseInt(transactionData[0]), new Transaction(Integer.parseInt(transactionData[0]), Integer.parseInt(transactionData[1]), Integer.parseInt(transactionData[2]),type, Integer.parseInt(transactionData[4]),transactionData[5]));
				}
				reader.close();
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("Error, Transaction data file not found.");
			} catch(IOException e) {
				throw new IOException("Error reading Transaction data file.");
			}
		}
	}

	@Override
	public ArrayList<Transaction> getTransactions(Customer customer, Account account) throws SQLException, IOException {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		if(!isUseDatabase()) {
			for(Transaction t : data.getTransactionList().values()) {
				if(customer.getCustomerId() == t.getCustomerId() && account.getId() == t.getAccountId()) {
					transactions.add(t);
				}
			}
			return transactions;
		}
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from BANKING_APPLICATION_TRANSACTIONS where CUSTOMER_ID = "+customer.getCustomerId()+" AND ACCOUNT_ID = "+account.getId());
			while(rs.next()) {
				TransactionType type;
				if(rs.getString("TYPE").equals("Deposit")) {
					type = TransactionType.DEPOSIT;
				}
				else {
					type = TransactionType.WITHDRAW;
				}
				transactions.add(new Transaction(Integer.parseInt(rs.getString("ID")), Integer.parseInt(rs.getString("ACCOUNT_ID")), Integer.parseInt(rs.getString("CUSTOMER_ID")), type, Integer.parseInt(rs.getString("AMOUNT")), rs.getString("DATE")));
			}
			return transactions;
		} catch(SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error getting accounts from database");
		}
	}

	@Override
	public void addTransaction(Account account, Transaction transaction) throws SQLException, IOException {
		if(!isUseDatabase()) {

			int uniqueID;
			boolean unique = true;
			while(true) {
				uniqueID = (int) (Math.random() * 1000000);
				for(Transaction t  : data.getTransactionList().values()) {
					if(t.getId() == uniqueID) {
						unique = false;
						break;
					}
				}
				if(unique)
					break;
		}
		data.getTransactionList().put(uniqueID, new Transaction(uniqueID, account.getId(), account.getCustomerId(), transaction.getType(),transaction.getAmount(), transaction.getDate()));
		if(properties.getProperties().get("data-source").equals("file")) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getProperties().get("transactionData"), true));
				writer.write(uniqueID+":"+account.getId()+":"+account.getCustomerId()+":"+transaction.getType()+":"+transaction.getAmount()+":"+transaction.getDate());
				writer.newLine();
				writer.close();
			} catch(IOException e) {
				throw new IOException("Error writing new account to data file.");
			}
		}
	}
	else {
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
			PreparedStatement ps = conn.prepareStatement("Insert into BANKING_APPLICATION_TRANSACTIONS(ID, ACCOUNT_ID, CUSTOMER_ID, TYPE, AMOUNT, DATE) VALUES (BANKING_APPLICATION_TRANSACTIONS_PKSEQ.nextval, ? , ?, ?, ?, ?)");
			ps.setInt(1, account.getId());
			ps.setInt(2, account.getCustomerId());
			ps.setString(3, transaction.getType()==TransactionType.DEPOSIT ? "Deposit" : "Withdraw");
			ps.setInt(4, transaction.getAmount());
			ps.setString(5, transaction.getDate());
			ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new SQLException("Database error trying to add account.");
		}
	}
		
	}

	public Database getData() {
		return data;
	}

	public void setData(Database data) {
		this.data = data;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public boolean isUseDatabase() {
		return useDatabase;
	}

	public void setUseDatabase(boolean useDatabase) {
		this.useDatabase = useDatabase;
	}
	
	
}
