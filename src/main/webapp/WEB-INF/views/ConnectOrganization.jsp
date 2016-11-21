<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connect Organization</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br>
<h1>Connect Organization</h1>

<form action="manageorganization.htm" method="post">
<p>Where do you want to the organization to be connected?
<select name="city">
<option name="Boston">Boston</option>
<option name="Quincy">Quincy</option>
<option name="Malden">Malden</option>
<option name="Medford">Medford</option>
<option name="Cambridge">Cambridge</option>
<option name="Brookline">Brookline</option>
</select><br>
Do you want to connect a clinic or to a hospital?
<select name="type">
<option name="Clinic">Clinic</option>
<option name="Hospital">Hospital</option>
</select>
</p>
<input type="submit" name="submit" value="Next" />
</form><br><br>

<input type="button" value="Back" onclick="window.history.back()" />

</center>
</body>
</html>