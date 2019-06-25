# BankingSpringWebApp
This project is a revamp of the original banking application done in Spring Boot. All business logic has been compiled into this project. All neccessary components/services are annotated with @Component/@Service and are @Autowired in the classes that need them. The web interface was done using Spring MVC. The main controller for the application is BankController.java under the com.banking.controller package. The JSP files can be found under src/main/webapp/jsp. The Data Access Layer was done using Spring JDBC. I have created some default data that is accessable without a database. When you run the application simply login as either of the following users:

Username: Customer1
Password: Password1

Username: Employee1
Password: Password1

and you will get access to the system. 
