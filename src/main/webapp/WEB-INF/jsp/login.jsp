<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Banking Application</title>
</head>
<body>
<h2>Login</h2>
<form action="/verifyLogin" method="post">
	<p>Username: <input type="text" name="username" style="margin-left: 5px;"></p>
	<p>Password: <input type="text" name="password" style="margin-left: 5px;"></p>
	<p><input type="submit" value="submit">
</form>
</body>
</html>