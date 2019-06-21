package com.banking.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.banking.data.Database;
import com.banking.entities.Properties;

@Configuration
public class Config{
	
	@Bean
	public Properties getProperties() {
		try {
			return new Properties("D:\\eclipse_workspace\\BankingWebApp\\properties.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Bean
	public Database getDatabase() {
		return new Database();
	}
}