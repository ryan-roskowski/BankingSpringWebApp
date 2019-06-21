package com.banking.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banking.entities.Employee;
import com.banking.entities.Properties;
import com.banking.data.Database;

@Component
public class EmployeeDaoImpl implements com.banking.dao.EmployeeDao {
	
	@Autowired
	Database data;
	
	@Autowired
	Properties properties;
	
	
	private boolean useDatabase;

	public EmployeeDaoImpl(Properties properties, Database data) throws ClassNotFoundException, IOException {
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
				reader = new BufferedReader(new FileReader(properties.getProperties().get("employeeData")));
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
	public Employee getEmployee(int userId) throws IOException, SQLException {
		if(!isUseDatabase()) {
			for(Employee e : data.getEmployeeList().values()) {
				if(e.getUserId() == userId) {
					return e;
				}
			}
			return null;
		}
		else {
			try {
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("Select * from BANKING_APPLICATION_EMPLOYEES where USERID = "+userId);
				if(rs.next() == false) {
					System.out.println("False");
					return null;
				}
				else {
					return new Employee(rs.getInt("ID"), rs.getInt("USERID"),rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("ADDRESS"), rs.getString("PHONE"), rs.getString("TYPE"));

				}
				
			} catch(SQLException e) {
				e.printStackTrace();
				throw new SQLException("Database error trying to get user.");
				
			}
		}
	}

	@Override
	public void addEmployee(int id, String password, String firstname, String lastname, String type)
			throws IOException, SQLException {
		// TODO Auto-generated method stub
		
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
