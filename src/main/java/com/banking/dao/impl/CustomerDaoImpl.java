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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banking.entities.Customer;
import com.banking.entities.Properties;
import com.banking.data.Database;

@Component
public class CustomerDaoImpl implements com.banking.dao.CustomerDao {
	
	@Autowired
	private Database data;
	
	@Autowired
	private Properties properties;
	
	private boolean useDatabase;
	public CustomerDaoImpl(Properties properties, Database data) throws ClassNotFoundException, IOException {
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
				reader = new BufferedReader(new FileReader(properties.getProperties().get("customerData")));
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
			try {
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("Select * from BANKING_APPLICATION_CUSTOMERS where USERID = "+userId);
				if(rs.next() == false) {
					return null;
				}
				else {
					
					Customer cust = new Customer(rs.getInt("ID"), rs.getInt("USERID"),rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("ADDRESS"), rs.getString("PHONE"));
					cust.setType("Customer");
					return cust;

				}
				
			} catch(SQLException e) {
				e.printStackTrace();
				throw new SQLException("Database error trying to get user.");
				
			}
		}
	}

	@Override
	public void addCustomerWithUserId(String username, String firstname, String lastname, String address, String phonenumber) throws IOException, SQLException {
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
			if(properties.getProperties().get("data-source").equals("file")) {
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getProperties().get("customerData"), true));
					writer.write(uniqueID+":"+userId+":"+firstname+":"+lastname+":"+address+":"+phonenumber);
					writer.newLine();
					writer.close();
				} catch(IOException e) {
					throw new IOException("Error writing new Customer to data file.");
				}
			}
		}
		else {
			try {
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT ID FROM BANKING_APPLICATION_USERS WHERE USERNAME = '"+username+"'");
				PreparedStatement ps = conn.prepareStatement("Insert into BANKING_APPLICATION_CUSTOMERS (ID, USERID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE) VALUES (BANKING_APPLICATION_CUSTOMERS_PKSEQ.nextval, ?, ?, ?, ?, ?)");
				if(rs.next() == false)
					throw new SQLException("User "+username+" does not exist, cannot add customer.");
				int userId = rs.getInt("ID");
				ps.setInt(1, userId);
				ps.setString(2, firstname);
				ps.setString(3, lastname);
				ps.setString(4, address);
				ps.setString(5, phonenumber);
				ps.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
				throw new SQLException("Database error trying to add customer.");
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
