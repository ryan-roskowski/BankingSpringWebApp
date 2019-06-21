<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>New Customer User</title>
</head>
<body>
<h2>Please enter customer information below: </h2><br>
<form action="/addCustomer" method="post">
	Customer Username:<input type="text" name="username" style="margin-left: 53px;"><br><br>
	Customer Password:<input type="text" name="password" style="margin-left: 57px;"><br><br>
	Customer First Name:&nbsp; <input type="text" name="fname" style="margin-left: 38px;"><br><br>
	Customer Last Name: &nbsp;&nbsp;<input type="text" name="lname" style="margin-left: 36px;"><br><br>
	Customer Phone Number:&nbsp;&nbsp; <input type="text" name="phone" style="margin-left: 10px;"><br><br>
	Customer Address: &nbsp;<input type="text" name="address" style="margin-left: 57px;"><br>
	<input type="submit" value="Submit">
</form>
</body>
</html>
