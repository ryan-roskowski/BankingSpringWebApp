package com.banking.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.banking.entities.Employee;

public interface EmployeeDao {
	public Employee getEmployee(int userId) throws IOException, SQLException;
	public void addEmployee(int id, String password, String firstname, String lastname, String type) throws IOException, SQLException;
}
