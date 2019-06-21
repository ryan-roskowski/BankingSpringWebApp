package com.banking.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.banking.entities.Customer;

public interface CustomerDao {
	public Customer getCustomer(int userId) throws SQLException;
	public void addCustomerWithUserId(String username, String firstname, String lastname, String address, String phonenumber) throws IOException, SQLException;
}
