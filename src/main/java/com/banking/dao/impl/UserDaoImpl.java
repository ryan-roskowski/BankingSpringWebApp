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

import com.banking.entities.User;
import com.banking.entities.DBProperties;
import com.banking.data.Database;

@Component
public class UserDaoImpl implements com.banking.dao.UserDao {
	
	@Autowired
	private Database data;
	
	@Autowired
	private DBProperties properties;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private boolean useDatabase;
	
	public UserDaoImpl(DBProperties properties, Database data) throws IOException, ClassNotFoundException  {
		if(properties.getDatasource() != null && properties.getDatasource().equals("database")) {
			this.useDatabase = true;
		}
		else if(properties.getDatasource() != null && properties.getDatasource().equals("file")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(properties.getUserData()));
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
	public User getUser(String username) {
		if(!isUseDatabase())
			return data.getUserList().get(username);
		else {
			return (User) jdbcTemplate.queryForObject("Select * from BANKING_APPLICATION_USERS where USERNAME = '"+username+"'", new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new User(rs.getInt("ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("TYPE"));
				}
			});
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
			
			if(properties.getDatasource().equals("file")) {
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getUserData(), true));
					writer.write(uniqueID+":"+username+":"+password+":"+type);
					writer.newLine();
					writer.close();
				} catch(IOException e) {
					throw new IOException("Error writing new User to data file.");
				}
				
			}
		}
		else {
				jdbcTemplate.execute("Insert into BANKING_APPLICATION_USERS (ID, USERNAME, PASSWORD, TYPE) VALUES (BANKING_APPLICATION_USERS_PKSEQ.nextval, '"+username+"', '"+password+"', '"+type+"')");
		}
	}
	
	

}
