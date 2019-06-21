<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Menu</title>
</head>
<body>
	<h2>Please select a menu item below:</h2>
	<form action="/menuAction" method="post">
	  <input type="radio" name="menu" value="1" checked>View_Balance<br>
	  <input type="radio" name="menu" value="2"> Deposit<br>
	  <input type="radio" name="menu" value="3"> Withdraw<br>
	  <input type="radio" name="menu" value="4"> View Statement<br>
	  <input type="radio" name="menu" value="5"> Logout<br>
	  <input type="submit" value="submit">
	</form>
</body>
</html>