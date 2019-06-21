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

import com.banking.entities.User;
import com.banking.entities.Properties;
import com.banking.data.Database;

@Component
public class UserDaoImpl implements com.banking.dao.UserDao {
	
	@Autowired
	private Database data;
	
	@Autowired
	private Properties properties;
	
	private boolean useDatabase;
	
	public UserDaoImpl(Properties properties, Database data) throws IOException, ClassNotFoundException  {
		if(properties.getProperties().get("data-source") != null && properties.getProperties().get("data-source").equals("database")) {
			this.useDatabase = true;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				throw new ClassNotFoundException("oracle.jdbc.driver.OracleDriver class not found.");
			}
		}
		else if(properties.getProperties().get("data-source") != null && properties.getProperties().get("data-source").equals("file")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(properties.getProperties().get("userData")));
				String line;
				String[] userData;
				while((line = reader.readLine()) != null) {
					userData = line.split(":");
					data.getUserList().put(userData[1], new User(Integer.parseInt(userData[0]), userData[1], userData[2], userData[3]));
				}
				reader.close();
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("Error, user data file not found.");
			} catch(IOException e) {
				throw new IOException("Error reading user data file.");
			}
			
		}
		
	}
	
	public boolean isUseDatabase() {
		return useDatabase;
	}

	public void setUseDatabase(boolean useDatabase) {
		this.useDatabase = useDatabase;
	}

	@Override
	public User getUser(String username) throws SQLException {
		if(!isUseDatabase())
			return data.getUserList().get(username);
		else {
			try {
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("Select * from BANKING_APPLICATION_USERS where USERNAME = '"+username+"'");
				if(rs.next() == false) {
					return null;
				}
				else {
					return new User(rs.getInt("ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("TYPE"));

				}
				
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				throw new SQLException("Database error trying to get user.");
				
			}
		}
	}

	@Override
	public void addUser(String username, String password, String type) throws Exception {
		if(!isUseDatabase()) {
			int uniqueID;
			boolean unique = true;
			if(data.getUserList().containsKey(username)) 
				throw new Exception("Error, username already in use.");
			else
			{
				
				while(true) {
					uniqueID = (int) (Math.random() * 1000000);
					for(User u : data.getUserList().values()) {
						if(u.getUserId() == uniqueID) {
							unique = false;
							break;
						}
					}
					if(unique)
						break;
				}
				
				data.getUserList().put(username, new User(uniqueID, username, password, type));
			}
			
			if(properties.getProperties().get("data-source").equals("file")) {
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getProperties().get("userData"), true));
					writer.write(uniqueID+":"+username+":"+password+":"+type);
					writer.newLine();
					writer.close();
				} catch(IOException e) {
					throw new IOException("Error writing new User to data file.");
				}
				
			}
		}
		else {
			try {
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
				Statement st = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement("Insert into BANKING_APPLICATION_USERS (ID, USERNAME, PASSWORD, TYPE) VALUES (BANKING_APPLICATION_USERS_PKSEQ.nextval, ?, ?, ?)");
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, type);
				ps.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
				throw new SQLException("Database error trying to add customer.");
			}
		}
	}
	
	

}
