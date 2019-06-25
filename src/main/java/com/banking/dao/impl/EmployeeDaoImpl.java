package com.banking.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.banking.entities.DBProperties;
import com.banking.entities.Employee;
import com.banking.data.Database;

@Component
public class EmployeeDaoImpl implements com.banking.dao.EmployeeDao {
	
	@Autowired
	Database data;
	
	@Autowired
	DBProperties properties;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private boolean useDatabase;

	public EmployeeDaoImpl(DBProperties properties, Database data) throws ClassNotFoundException, IOException {
		if(properties.getDatasource().equals("database")) {
			this.useDatabase = true;
		}
		else if(properties.getDatasource().equals("file")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(properties.getEmployeeData()));
				String line;
				String[] employeeData;
				while((line = reader.readLine()) != null) {
					employeeData = line.split(":");
					data.getEmployeeList().put(Integer.parseInt(employeeData[0]), new Employee(Integer.parseInt(employeeData[0]), Integer.parseInt(employeeData[1]), employeeData[2], employeeData[3],employeeData[4],employeeData[5],employeeData[6]));
				}
				reader.close();
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("Error, Employee data file not found.");
			} catch(IOException e) {
				throw new IOException("Error reading Employee data file.");
			}
		}
	}

	@Override
	public Employee getEmployee(int userId) throws IOException {
		if(!isUseDatabase()) {
			for(Employee e : data.getEmployeeList().values()) {
				if(e.getUserId() == userId) {
					return e;
				}
			}
			return null;
		}
		else {
			return (Employee) jdbcTemplate.queryForObject("Select * from BANKING_APPLICATION_EMPLOYEES where USERID = "+userId, new RowMapper<Employee>() {
				@Override
				public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Employee(rs.getInt("ID"), rs.getInt("USERID"),rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("ADDRESS"), rs.getString("PHONE"), rs.getString("TYPE"));
				}
				
			});
		}
	}

	@Override
	public void addEmployee(int id, String password, String firstname, String lastname, String type)
			throws IOException {
		// TODO Auto-generated method stub
		
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
