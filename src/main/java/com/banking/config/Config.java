package com.banking.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.banking.data.Database;

@Configuration
public class Config{
	
	@Bean
	public Database getDatabase() {
		return new Database();
	}
}