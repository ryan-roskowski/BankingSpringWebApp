package com.banking.services;

import java.io.IOException;
import java.sql.SQLException;

public interface EmployeeService {
	public void createCustomerUser(String username, String password, String firstname, String lastname, String address, String phonenumber) throws Exception;
}
