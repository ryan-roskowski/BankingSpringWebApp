package com.banking;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.banking.entities.Properties;

@SpringBootApplication
public class BankingSpringWebAppApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BankingSpringWebAppApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(BankingSpringWebAppApplication.class, args);
	}

}
