<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-1.12.1.min.js"></script>
<script src="http://www.datejs.com/build/date.js" type="text/javascript"></script>
<script>
$(document).ready(function(){
	var mindate = new Date();
	mindate.setDate(mindate.getDate());
	mindate = mindate.toString("yyyy-MM-dd");
	var maxdate = new Date();
	maxdate.setDate(maxdate.getDate() + 2);
	maxdate = maxdate.toString("yyyy-MM-dd");

    $("#date").attr("min", mindate);
    $("#date").attr("max", maxdate);
    $("#date").val(mindate);
});
</script>
<title>Patient Page</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br>
<h1>Patient Page</h1>

<form action="patientselectorganization.htm" method="post">
Where do you live
<select name="city">
<option name="Boston">Boston</option>
<option name="quincy">Quincy</option>
<option name="malden">Malden</option>
<option name="medford">Medford</option>
<option name="cambridge">Cambridge</option>
<option name="brookline">Brookline</option>
</select><br><br>
Please select the type of the organization that you want to make an appointment
<select name="type">
<option name="clinic">Clinic</option>
<option name="hospital">Hospital</option>
</select><br><br>
Please select the date
<input id="date" type="date" name="date" />
<br><br><br>
<input type="submit" name="submit" value="Next"></input>
</form>
<br>
<form action="home.htm" method="get">
    <input type="submit" value="Back to the main page">
</form>
</center>
</body>
</html>