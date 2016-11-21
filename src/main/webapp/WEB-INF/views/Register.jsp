<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Account</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<br><br>
<center>
<h1>
Register
</h1>
<form:form action="registeraccount.htm" modelAttribute="account" method="post">
Username:<form:input type="text" path="username" maxlength='30' /><br><font color="red"><form:errors path="username"/></font><br>
Password:<form:input type="password" path="password" maxlength='30' /><br><font color="red"><form:errors path="password"/></font><br>
Confirm Password:<input type="password" name="cpassword" maxlength='30' /><br><br>
Role:<select name="role">
<option>Patient</option>
<option>Doctor</option>
<option>Pharmacist</option>
</select><br><br>

<br><input type="submit" name="submit" value="Next"></input><br><br>
</form:form>
<c:if test="${not empty error1}">
   <p style="color:red;">Error: ${requestScope.error1}</p>
</c:if>
<form action="home.htm" method="get">
    <input type="submit" value="Back to the main page">
</form>
</center>
</body>
</html>