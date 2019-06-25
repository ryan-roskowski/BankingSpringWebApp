package com.banking.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.banking.entities.Customer;
import com.banking.entities.DBProperties;
import com.banking.data.Database;

@Component
public class CustomerDaoImpl implements com.banking.dao.CustomerDao {
	
	@Autowired
	private Database data;
	
	@Autowired
	private DBProperties properties;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private boolean useDatabase;
	public CustomerDaoImpl(DBProperties properties, Database data) throws ClassNotFoundException, IOException {
		if(properties.getDatasource().equals("database")) {
			this.useDatabase = true;
		}
		else if(properties.getDatasource().equals("file")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(properties.getCustomerData()));
				String line;
				String[] customerData;
				while((line = reader.readLine()) != null) {
					customerData = line.split(":");
					data.getCustomerList().put(Integer.parseInt(customerData[0]), new Customer(Integer.parseInt(customerData[0]), Integer.parseInt(customerData[1]), customerData[2], customerData[3],customerData[4],customerData[5]));
				}
				reader.close();
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("Error, Customer data file not found.");
			} catch(IOException e) {
				throw new IOException("Error reading Customer data file.");
			}
		}
	}

	@Override
	public Customer getCustomer(int userId) throws SQLException {
		if(!isUseDatabase()) {
			for(Customer c : data.getCustomerList().values()) {
				if(c.getUserId() == userId) {
					return c;
				}
			}
			return null;
		}
		else {
			return (Customer) jdbcTemplate.queryForObject("Select * from BANKING_APPLICATION_CUSTOMERS where USERID = "+userId, new RowMapper<Customer>() {
				@Override
				public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Customer(rs.getInt("ID"), rs.getInt("USERID"),rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("ADDRESS"), rs.getString("PHONE"));
				}
				
			});
		}
	}

	@Override
	public void addCustomerWithUserId(String username, String firstname, String lastname, String address, String phonenumber) throws IOException, SQLException {
		System.out.println("Trying to add Customer");
		if(!isUseDatabase()) {
			int uniqueID;
			boolean unique = true;
			while(true) {
				uniqueID = (int) (Math.random() * 1000000);
				for(Customer c  : data.getCustomerList().values()) {
					if(c.getCustomerId() == uniqueID) {
						unique = false;
						break;
					}
				}
				if(unique)
					break;
			}
			int userId = data.getUserList().get(username).getUserId();
			data.getCustomerList().put(uniqueID, new Customer(uniqueID, userId, firstname, lastname, address, phonenumber));
			if(properties.getDatasource().equals("file")) {
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getCustomerData(), true));
					writer.write(uniqueID+":"+userId+":"+firstname+":"+lastname+":"+address+":"+phonenumber);
					writer.newLine();
					writer.close();
				} catch(IOException e) {
					throw new IOException("Error writing new Customer to data file.");
				}
			}
		}
		else {
			int userId = Integer.parseInt((String) (jdbcTemplate.queryForObject("SELECT ID FROM BANKING_APPLICATION_USERS WHERE USERNAME = '"+username+"'", String.class)));
			jdbcTemplate.execute("Insert into BANKING_APPLICATION_CUSTOMERS (ID, USERID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE) VALUES (BANKING_APPLICATION_CUSTOMERS_PKSEQ.nextval, "+userId+", '"+firstname+"', '"+lastname+"', '"+address+"', '"+phonenumber+"')");
		}
	}

	public Database getData() {
		return data;
	}

	public void setData(Database data) {
		this.data = data;
	}

	public DBProperties getProperties() {
		return properties;
	}

	public void setProperties(DBProperties properties) {
		this.properties = properties;
	}

	public boolean isUseDatabase() {
		return useDatabase;
	}

	public void setUseDatabase(boolean useDatabase) {
		this.useDatabase = useDatabase;
	}
}
