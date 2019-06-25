package com.banking.data;

import java.util.HashMap;

import com.banking.entities.User;
import com.banking.enums.TransactionType;
import com.banking.entities.Account;
import com.banking.entities.Customer;
import com.banking.entities.Employee;
import com.banking.entities.Transaction;

public class Database {
	HashMap<String, User> userList;
	HashMap<Integer, Customer> customerList;
	HashMap<Integer, Employee> employeeList;
	HashMap<Integer, Account> accountList;
	HashMap<Integer, Transaction> transactionList;
	
	public Database() {
		userList = new HashMap<String, User>();
		customerList = new HashMap<Integer, Customer>();
		employeeList = new HashMap<Integer, Employee>();
		accountList = new HashMap<Integer, Account>();
		transactionList = new HashMap<Integer, Transaction>();
		
	}
	
	public HashMap<String, User> getUserList() {
		return userList;
	}




	public void setUserList(HashMap<String, User> userList) {
		this.userList = userList;
	}




	public HashMap<Integer, Customer> getCustomerList() {
		return customerList;
	}




	public void setCustomerList(HashMap<Integer, Customer> customerList) {
		this.customerList = customerList;
	}




	public HashMap<Integer, Employee> getEmployeeList() {
		return employeeList;
	}




	public void setEmployeeList(HashMap<Integer, Employee> employeeList) {
		this.employeeList = employeeList;
	}




	public HashMap<Integer, Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(HashMap<Integer, Account> accountList) {
		this.accountList = accountList;
	}
	
	public HashMap<Integer, Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(HashMap<Integer, Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	public void generateDefaultData(){
		userList.put("employee1", new User(1, "employee1", "password1", "Employee"));
		userList.put("employee2", new User(2, "employee2", "password2", "Employee"));
		userList.put("employee3", new User(3, "employee3", "password3", "Employee"));
		userList.put("employee4", new User(4, "employee4", "password4", "Employee"));
		userList.put("customer1", new User(5, "customer1", "password1", "Customer"));
		userList.put("customer2", new User(6, "customer2", "password2", "Customer"));
		userList.put("customer3", new User(7, "customer3", "password3", "Customer"));
		userList.put("customer4", new User(8, "customer4", "password4", "Customer"));
		
		employeeList.put(1, new Employee(1, 1,"Employee1FirstName", "Employee1LastName", "Employee1Address", "Employee1Phone", "Manager"));
		employeeList.put(2, new Employee(2, 2,"Employee2FirstName", "Employee2LastName", "Employee2Address", "Employee2Phone", "Manager"));
		employeeList.put(3, new Employee(3, 3,"Employee3FirstName", "Employee3LastName", "Employee3Address", "Employee3Phone", "Teller"));
		employeeList.put(4, new Employee(4, 4,"Employee4FirstName", "Employee4LastName", "Employee4Address", "Employee4Phone", "Teller"));
		
		customerList.put(1,  new Customer(1, 5, "Customer1FirstName", "Customer1LastName", "Customer1Address", "Customer1Phone"));
		customerList.put(2,  new Customer(2, 6, "Customer2FirstName", "Customer2LastName", "Customer2Address", "Customer2Phone"));
		customerList.put(3,  new Customer(3, 7, "Customer3FirstName", "Customer3LastName", "Customer3Address", "Customer3Phone"));
		customerList.put(4,  new Customer(4, 8, "Customer4FirstName", "Customer4LastName", "Customer4Address", "Customer4Phone"));
		
		accountList.put(1, new Account(1, 100000000, 1, 100, "Checking"));
		accountList.put(2, new Account(2, 100000001, 2, 200, "Savings"));
		accountList.put(3, new Account(3, 100000002, 3, 300, "Checking"));
		accountList.put(4, new Account(4, 100003000, 4, 400, "Savings"));
		accountList.put(5, new Account(5, 130004400, 1, 100, "Savings"));
		//yyyy/MM/dd
		transactionList.put(1, new Transaction(1, 1, 1,TransactionType.DEPOSIT, 1000, "2019/01/12"));
		transactionList.put(2, new Transaction(2, 1, 1,TransactionType.WITHDRAW, 500, "2019/01/12"));
		transactionList.put(3, new Transaction(3, 1, 1,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(4, new Transaction(4, 1, 1,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(5, new Transaction(5, 1, 1,TransactionType.WITHDRAW, 200, "2019/01/12"));
				
		transactionList.put(6, new Transaction(6, 2,2,TransactionType.DEPOSIT, 1000, "2019/01/12"));
		transactionList.put(7, new Transaction(7, 2,2,TransactionType.WITHDRAW, 600, "2019/01/12"));
		transactionList.put(8, new Transaction(8, 2,2,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(9, new Transaction(9, 2,2,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(10, new Transaction(10, 2,2,TransactionType.WITHDRAW, 200, "2019/01/12"));
		
		transactionList.put(11, new Transaction(11, 3,3,TransactionType.DEPOSIT, 1000, "2019/01/12"));
		transactionList.put(12, new Transaction(12, 3,3,TransactionType.WITHDRAW, 700, "2019/01/12"));
		transactionList.put(13, new Transaction(13, 3,3,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(14, new Transaction(14, 3,3,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(15, new Transaction(15, 3,3,TransactionType.WITHDRAW, 200, "2019/01/12"));
		
		transactionList.put(16, new Transaction(16, 4,4,TransactionType.DEPOSIT, 1000, "2019/01/12"));
		transactionList.put(17, new Transaction(17, 4,4,TransactionType.WITHDRAW, 800, "2019/01/12"));
		transactionList.put(18, new Transaction(18, 4,4,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(19, new Transaction(19, 4,4,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(20, new Transaction(20, 4,4,TransactionType.WITHDRAW, 200, "2019/01/12"));
		
		transactionList.put(21, new Transaction(21, 5, 1,TransactionType.DEPOSIT, 1000, "2019/01/12"));
		transactionList.put(22, new Transaction(22, 5, 1,TransactionType.WITHDRAW, 500, "2019/01/12"));
		transactionList.put(23, new Transaction(23, 5, 1,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(24, new Transaction(24, 5, 1,TransactionType.WITHDRAW, 100, "2019/01/12"));
		transactionList.put(25, new Transaction(25, 5, 1,TransactionType.WITHDRAW, 200, "2019/01/12"));
		
	}
}
