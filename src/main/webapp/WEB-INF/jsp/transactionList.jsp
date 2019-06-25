<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.ArrayList" %>
 <%@ page import="com.banking.entities.Transaction" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transaction List</title>
</head>
<body>
<h2>Transactions for ${name}'s account ${accountNumber}</h2>
<table style="border-collapse: collapse; border-spacing: 5px; width: 600px;">
<tr><td>Date</td><td>Type</td><td>Amount</td></tr>
	<c:forEach items="${transactionList}" var="transaction">
		<tr style="border: 1px solid black;"> 
			 <td style="text-align: center;">${transaction.date}</td>
			 <td style="text-align: center;">${transaction.type}</td>
			 <td style="text-align: center;">${transaction.amount}</td>
		</tr>
	</c:forEach>
</table>
<br>
<a href="/">Main Menu</a>
</body>
</html>