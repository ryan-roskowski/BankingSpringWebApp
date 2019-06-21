<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Teller Menu</title>
</head>
<body>
	<h2>Please select a menu item below:</h2>
	<form action="/menuAction" method="post">
	  <input type="radio" name="menu" value="1" checked>Create Customer With Login<br>
	  <input type="radio" name="menu" value="2"> Create Account For Customer<br>
	  <input type="radio" name="menu" value="3"> Teller Option 1 <br>
	  <input type="radio" name="menu" value="4"> Teller Option 2 <br>
	  <input type="radio" name="menu" value="5"> Logout <br>
	  <input type="hidden" name="purpose" value="action">
	  <input type="submit" value="submit">
	</form>
</body>
</html>