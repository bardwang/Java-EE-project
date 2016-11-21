<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br>
<h1>
Health Appointment  
</h1>
<form:form action="login.htm" modelAttribute="account" method="post">
Username:<form:input type="text" path="username" maxlength='30' /><br><font color="red"><form:errors path="username"/></font><br>
Password:<form:input type="password" path="password" maxlength='30' /><br><font color="red"><form:errors path="password"/></font><br>
<input type="submit" name="submit" value="Login"></input></form:form>
<c:if test="${not empty error1}">
   <p style="color:red;">Error: ${requestScope.error1}</p>
</c:if><br>
<form action="register.htm">
    <input type="submit" value="Register">
</form>

</center>
</body>
</html>
