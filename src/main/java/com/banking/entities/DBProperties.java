package com.banking.entities;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("db")
public class DBProperties {
	private String driverclassname;
	private String username;
	private String password;
	private String url;
	private String datasource;
	private String userData;
	private String customerData;
	private String employeeData;
	private String accountData;
	private String transactionData;
	public String getDriverclassname() {
		return driverclassname;
	}
	public void setDriverclassname(String driverclassname) {
		this.driverclassname = driverclassname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
	public String getCustomerData() {
		return customerData;
	}
	public void setCustomerData(String customerData) {
		this.customerData = customerData;
	}
	public String getEmployeeData() {
		return employeeData;
	}
	public void setEmployeeData(String employeeData) {
		this.employeeData = employeeData;
	}
	public String getAccountData() {
		return accountData;
	}
	public void setAccountData(String accountData) {
		this.accountData = accountData;
	}
	public String getTransactionData() {
		return transactionData;
	}
	public void setTransactionData(String transactionData) {
		this.transactionData = transactionData;
	}
	@Override
	public String toString() {
		return "DBProperties [driverclassname=" + driverclassname + ", username=" + username + ", password=" + password
				+ ", url=" + url + "]";
	}
	
	@Bean
	public DataSource datasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(this.driverclassname);
		dataSource.setUrl(this.getUrl());
        dataSource.setUrl(this.getUrl());
        dataSource.setUsername(this.getUsername());
        dataSource.setPassword(this.getPassword());
        return dataSource;
	}
	
	@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
	}
}
