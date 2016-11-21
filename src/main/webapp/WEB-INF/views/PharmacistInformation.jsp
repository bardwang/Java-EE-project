<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Pharmacist Account</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br>
<h1>
Register Pharmacist Account
</h1>
<form:form action="pharmacistregisteraccount.htm" modelAttribute="pharmacistaccount" method="post">
Name:<form:input type="text" path="name" maxlength='30' /><br><font color="red"><form:errors path="name"/></font><br>
Telephone:<form:input type="text" path="telephone" maxlength='10' /><br><font color="red"><form:errors path="telephone"/></font><br>
Where do you live
<select name="city">
<option name="Boston">Boston</option>
<option name="quincy">Quincy</option>
<option name="malden">Malden</option>
<option name="medford">Medford</option>
<option name="cambridge">Cambridge</option>
<option name="brookline">Brookline</option>
</select><br><br>
Please select the type of the organization that you want to work in
<select name="type">
<option name="clinic">Clinic</option>
<option name="hospital">Hospital</option>
</select><br><br>
<input type="submit" name="submit" value="Next"></input>
</form:form><br>
<input type="button" value="Back" onclick="window.history.back()" />
</center>

</body>
</html>