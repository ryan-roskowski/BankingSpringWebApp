<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New Account</title>
</head>
<body>
<h2>Please enter Account information below: </h2>
<form action="/addAccount" method="post">
	Customer Username: <input type="text" name="username"><br><br>
	Account Type: <br>
	<input type="radio" name="type" value="Checking"> Checking<br>
	<input type="radio" name="type" value="Savings"> Savings <br>
	<input type="submit" value="Submit"> <br>
</form>
</body>
</html>