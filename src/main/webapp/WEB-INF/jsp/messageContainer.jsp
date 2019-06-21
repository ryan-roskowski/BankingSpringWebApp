<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<meta charset="ISO-8859-1">
<title>Success!</title>
</head>
<body>
<h2>${message}</h2>
<a href="${contextPath}/">Main Menu</a>
</body>
</html>