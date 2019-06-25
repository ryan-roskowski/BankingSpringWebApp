package com.banking.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.dao.impl.CustomerDaoImpl;
import com.banking.dao.impl.UserDaoImpl;

@Service
public class EmployeeServiceImpl implements com.banking.services.EmployeeService {
	
	@Autowired
	UserDaoImpl userDao;
	
	@Autowired
	CustomerDaoImpl customerDao;
	
	@Override
	public void createCustomerUser(String username, String password, String firstname, String lastname, String address, String phonenumber) throws Exception {
		userDao.addUser(username, password, "Customer");
		customerDao.addCustomerWithUserId(username, firstname, lastname, address, phonenumber);
	}
	
}
