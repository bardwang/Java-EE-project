<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Manager</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br>
<h1>Welcome Manager</h1>

<form action="goapprovedoctor.htm" method="post">
	<input type="submit" name="submit" value="Approve Doctor Accounts" />
</form><br><br>
<form action="goapprovepharmacist.htm" method="post">
	<input type="submit" name="submit" value="Approve Pharmacist Accounts" />
</form><br><br>
<form action="goconnectorganization.htm" method="post">
	<input type="submit" name="submit" value="Connect Organizations" />
</form><br><br>
<br>

<form action="home.htm" method="get">
    <input type="submit" value="Back to the main page">
</form>

</center>

</body>
</html>