<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.ArrayList" %>
 <%@ page import="com.banking.entities.Account" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Select Account</title>
</head>
<body>
<h2>Please select an account below.</h2>
<form action="/withdraw" method="post">
	<c:forEach items="${accountList}" var="account" varStatus="index">     
		<c:if test="${index.index == '0'}">
	  	<input type="radio" name="account" value="${index.index}" checked="checked"> ${account.type}
	  	</c:if>
	  	<c:if test="${index.index != '0'}">
	  	<input type="radio" name="account" value="${index.index}"> ${account.type}
	  	</c:if>
	  	<br>
	</c:forEach>  
   <br>Withdraw Amount: <input type="text" name="amount">
    <br><br>
    <input type="submit" value="submit">
</form>
</body>
</html>