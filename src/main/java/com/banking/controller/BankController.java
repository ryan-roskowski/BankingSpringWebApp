package com.banking.controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.beans.*;
import com.bank.dao.*;
import com.bank.dao.impl.*;
import com.bank.data.Database;
import com.bank.enums.DepositResult;
import com.bank.enums.UserAction;
import com.bank.enums.WithdrawResult;
import com.bank.controller.*;
import com.bank.services.*;
import com.bank.services.impl.*;

@Controller
public class BankController {
	private Database data;
	private Properties properties;
	private UserDao userDao;
	private CustomerDao customerDao;
	private EmployeeDao employeeDao;
	private AccountDao accountDao;
	private TransactionDao transactionDao;
	private UserService userService;
	private EmployeeService employeeService;
	private AccountService accountService;
	private LoginController loginController;
	private AccountController accountController;
	
	public BankController() {
		try {
			data = new Database();
			properties = new Properties("properties.txt");
			userDao = new UserDaoImpl(properties, data);
			customerDao = new CustomerDaoImpl(properties, data);
			employeeDao = new EmployeeDaoImpl(properties, data);
			accountDao = new AccountDaoImpl(properties, data);
			transactionDao = new TransactionDaoImpl(properties, data);
			userService = new UserServiceImpl();
			employeeService = new EmployeeServiceImpl((UserDaoImpl)userDao, (CustomerDaoImpl)customerDao);
			accountService = new AccountServiceImpl((AccountDaoImpl)accountDao, (TransactionDaoImpl)transactionDao);
			loginController = new LoginController((UserDaoImpl)userDao, (EmployeeDaoImpl)employeeDao, (CustomerDaoImpl)customerDao);
			accountController = new AccountController((AccountDaoImpl)accountDao, (TransactionDaoImpl)transactionDao);
			data.generateDefaultData();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/")
	public String root(HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			return "login";
		}
		else {
			return loadMenu((User) request.getSession().getAttribute("user"));
		}
	}
	
	@PostMapping("verifyLogin")
	public String verifyLogin(HttpServletRequest request) {
		try {
			User user = loginController.login(request.getParameter("username"), request.getParameter("password"));
			if(user == null) {
				return "invalidUser";
			}
			else {
				request.getSession().setAttribute("user", user);
				return loadMenu(user);
				
			}
		} catch (SQLException | IOException e) {
			return "error";
		}
	}
	
	@PostMapping("menuAction")
	public String menuAction(Model model, HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		UserAction userAction = loginController.performAction(user, request.getParameter("menu"));
		switch(userAction) {
		case CREATE_ACCOUNT_FOR_CUSTOMER:
			return "newAccount";
		case CREATE_CUSTOMER:
			return "newCustomer";
		case DEPOSIT:
			try {
				Customer customer = (Customer) user;
				customer.setAccounts(accountController.getAccounts(customer));
				if(customer.getAccounts().isEmpty()) {
					model.addAttribute("message", "This user has no accounts, please have an employee add an account.");
					return "messageContainer";
				}
				else {
					model.addAttribute("accountList", customer.getAccounts());
					return "depositAccountSelect";
				}
			} catch (SQLException e) {
				return "error";
			}
		case INVALID:
			request.getSession().invalidate();
			try {
				response.sendRedirect("/");
				return null;
			} catch (IOException e) {
				return "error";
			}
		case LOGOUT:
			request.getSession().invalidate();
			try {
				response.sendRedirect("/");
				return null;
			} catch (IOException e) {
				return "error";
			}
		case MANAGER_1:
			model.addAttribute("message", "Performed Manager Option 1");
			return "messageContainer";
		case MANAGER_2:
			model.addAttribute("message", "Performed Manager Option 2");
			return "messageContainer";
		case TELLER_1:
			model.addAttribute("message", "Performed Teller Option 1");
			return "messageContainer";
		case TELLER_2:
			model.addAttribute("message", "Performed Teller Option 2");
			return "messageContainer";
		case VIEW_BALANCE:
			try {
				Customer customer = (Customer) user;
				customer.setAccounts(accountController.getAccounts(customer));
				if(customer.getAccounts().isEmpty()) {
					model.addAttribute("message", "This user has no accounts, please have an employee add an account.");
					return "messageContainer";
				}
				else {
					model.addAttribute("accountList", customer.getAccounts());
					return "viewBalanceAccountSelect";
					
				}
			} catch (SQLException e1) {
				return "error";
			}
		case VIEW_TRANSACTIONS:
			try {
				Customer customer = (Customer) user;
				customer.setAccounts(accountController.getAccounts(customer));
				if(customer.getAccounts().isEmpty()) {
					model.addAttribute("message", "This user has no accounts, please have an employee add an account.");
					return "messageContainer";
				}
				else {
					model.addAttribute("accountList", customer.getAccounts());
					return "statementAccountSelect";
				}
			}
			catch (SQLException e1) {
				return "error";
		}
		case WITHDRAW:
			try {
				Customer customer = (Customer) user;
				customer.setAccounts(accountController.getAccounts(customer));
				if(customer.getAccounts().isEmpty()) {
					model.addAttribute("message", "This user has no accounts, please have an employee add an account.");
					return "messageContainer";
				}
				else {
					model.addAttribute("accountList", customer.getAccounts());
					return "withdrawAccountSelect";
				}
			} catch (SQLException e1) {
				return "error";
			}
		default:
			return "error";
		
		}
	}
	
	@PostMapping("/viewBalance")
	public String viewBalance(Model model, HttpServletRequest request) {
		try {
			User user = (User)request.getSession().getAttribute("user");
			Customer customer = (Customer) user;
			customer.setAccounts(accountController.getAccounts(customer));
			if(customer.getAccounts().isEmpty()) {
				model.addAttribute("message", "This user has no accounts, please have an employee add an account.");
				return "messageContainer";
				
			}
			else {
				model.addAttribute("message", "Balance: $"+customer.getAccounts().get(Integer.parseInt(request.getParameter("account"))).getBalance());
				return "messageContainer";
			}
		}
		catch(Exception e) {
			return "error";
		}
	}
	
	@PostMapping("/deposit")
	public String deposit(Model model, HttpServletRequest request) {
		try {
			User user = (User)request.getSession().getAttribute("user");
			Customer customer = (Customer) user;
			customer.setAccounts(accountController.getAccounts(customer));
			if(customer.getAccounts().isEmpty()) {
				model.addAttribute("message", "This user has no accounts, please have an employee add an account.");
				return "messageContainer";
			}
			else {
				long prevBalance = customer.getAccounts().get(Integer.parseInt( (String) request.getParameter("account"))).getBalance();
				DepositResult res = accountService.deposit(customer.getAccounts().get(Integer.parseInt( (String) request.getParameter("account"))), (Integer.parseInt( (String) request.getParameter("amount"))));
				if(res == DepositResult.SUCCESS) {
					model.addAttribute("message", "Previous balance: $"+prevBalance+"\nNew Balance: $"+customer.getAccounts().get(Integer.parseInt( (String) request.getParameter("account"))).getBalance());
					return "messageContainer";
				}
				else {
					model.addAttribute("message", "Deposit failed.");
					return "messageContainer";
				}
			}
		}
		catch(Exception e) {
			return "error";
		}
	}
	
	@PostMapping("/withdraw")
	public String withdraw(Model model, HttpServletRequest request) {
		try {
			User user = (User)request.getSession().getAttribute("user");
			Customer customer = (Customer) user;
			customer.setAccounts(accountController.getAccounts(customer));
			if(customer.getAccounts().isEmpty()) {
				model.addAttribute("message", "This user has no accounts, please have an employee add an account.");
				return "messageContainer";
			}
			else {
				long prevBalance = customer.getAccounts().get(Integer.parseInt( (String) request.getParameter("account"))).getBalance();
				WithdrawResult res = accountService.withdraw(customer.getAccounts().get(Integer.parseInt( (String) request.getParameter("account"))), (Integer.parseInt( (String) request.getParameter("amount"))));
				if(res == WithdrawResult.SUCCESS) {
					model.addAttribute("message", "Previous balance: $"+prevBalance+"\nNew Balance: $"+customer.getAccounts().get(Integer.parseInt( (String) request.getParameter("account"))).getBalance());
					return "messageContainer";
				}
				else if(res == WithdrawResult.FAILURE)  {
					model.addAttribute("message", "Withdraw failed.");
					return "messageContainer";
				}
				else if(res == WithdrawResult.OVERDRAFT) {
					model.addAttribute("message", "Error, withdraw results in negative balance.");
					return "messageContainer";
				}
				else {
					return "error";
				}
			}
		}
		catch(Exception e) {
			return "error";
		}
	}
	
	@PostMapping("/viewStatement")
	public String viewStatement(Model model, HttpServletRequest request) {
		try {
			User user = (User)request.getSession().getAttribute("user");
			Customer customer = (Customer) user;
			customer.setAccounts(accountController.getAccounts(customer));
			if(customer.getAccounts().isEmpty()) {
				model.addAttribute("message", "This user has no accounts, please have an employee add an account.");
				return "messageContainer";
			}
			else {
				ArrayList<Transaction> transactionList = transactionDao.getTransactions(customer, customer.getAccounts().get(Integer.parseInt( (String) request.getParameter("account"))));
				model.addAttribute("transactionList", transactionList);
				model.addAttribute("name", customer.getFirstName()+" "+customer.getLastName());
				model.addAttribute("accountNumber", customer.getAccounts().get(Integer.parseInt( (String) request.getParameter("account"))).getAccountNumber());
				return "transactionList";
			}
		}
		catch(Exception e) {
			return "error";
		}
	}
	
	@PostMapping("/addCustomer")
	public String addCustomer(Model model, HttpServletRequest request) {
		if(request.getParameter("username").equals("") || request.getParameter("password").equals("") || request.getParameter("fname").equals("")
				|| request.getParameter("lname").equals("") || request.getParameter("address").equals("") || request.getParameter("phone").equals("")){
					model.addAttribute("message", "Incomplete input given.");
					return "messageContainer";
				}
				try {
					employeeService.createCustomerUser(request.getParameter("username"), request.getParameter("password"), request.getParameter("fname"),
							request.getParameter("lname"), request.getParameter("address"), request.getParameter("phone"));
					request.setAttribute("message", "Added Customer: Username="+request.getParameter("username")+", Password="+request.getParameter("username")+
							", FirstName="+request.getParameter("fname")+", LastName="+request.getParameter("lname")+", Address="+request.getParameter("address")+
							", Phone="+request.getParameter("phone"));
					return "messageContainer";
					
				} catch(Exception e) {
					return "error";
				}
	}
	
	@PostMapping("/addAccount")
	public String addAccount(Model model, HttpServletRequest request) {
		if(request.getParameter("username").equals("")) {
			model.addAttribute("message", "Incomplete input given.");
			return "messageContainer";
		}
		try {
			User accUser = userDao.getUser(request.getParameter("username"));
			if(accUser == null) {
				model.addAttribute("message", "You entered an invalid User.");
				return "messageContainer";
			}
			else {
				Customer accCustomer = customerDao.getCustomer(accUser.getUserId());
				if(accCustomer == null) {
					model.addAttribute("message", "You entered an invalid User.");
					return "messageContainer";
				}
				else {
					accountDao.addAccount(accCustomer, request.getParameter("type"));
					model.addAttribute("message", "Added Account: Username="+accUser.getUsername()+", type="+request.getParameter("type")+".");
					return "messageContainer";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	public String loadMenu(User user) {
		switch (user.getType()) {
		case "Employee":
			Employee employee = (Employee) user;
			switch (employee.getEmployeeType()) {
			case "Manager":
				return "ManagerMenu";
			case "Teller":
				return "TellerMenu";
			default:
				return "error";
			}
		case "Customer":
			return "CustomerMenu";
		default:
			return "error";
		}
	}
}
