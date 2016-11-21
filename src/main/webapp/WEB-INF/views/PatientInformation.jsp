<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Patient Information</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br>
<h1>
Register Patient Account
</h1>
<form:form action="patientregisteraccount.htm" modelAttribute="patientaccount" method="post">
Name:<form:input type="text" path="name" maxlength='30' /><br><font color="red"><form:errors path="name"/></font><br>
Address:<form:input type="text" path="address" maxlength='50' /><br><font color="red"><form:errors path="address"/></font><br>
Phone:<form:input type="text" path="phone" maxlength='10' /><br><font color="red"><form:errors path="phone"/></font><br>
Email:<form:input type="text" path="email" maxlength='50' /><br><font color="red"><form:errors path="email"/></font><br>
<input type="submit" name="submit" value="Submit"></input>
</form:form>
<br>
<input type="button" value="Back" onclick="window.history.back()" />
</center>
</body>
</html>