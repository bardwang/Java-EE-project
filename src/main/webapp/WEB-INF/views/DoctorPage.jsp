<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-1.12.1.min.js"></script>
<script>
$(document).ready(function(){
    $.ajax({
    	cache: false,
        type: "POST",
        url: "appointmenttable.htm",
        success: function(data) {
        debugger;
            var jsondata = JSON.parse(data);
            var tabledata = jsondata.list;
            tabledata.forEach(function(entry) {
            	$("#pages").empty();
                $("#table").append("<tr><td>" + entry.patientname + "</td>" + 
                					   "<td>" + entry.time + ":00</td>" +
                					   "<td>" + entry.reservationnumber + "</td>" +
                					   "<td><button id='select' name='" + entry.patientid + "'>select</button></td><tr>");
            });
        }
    });
    
    $(document).on("click", '#select',function(){
    	debugger;
    	$("#organform").empty();
    	$('button').each(function() {
        $(this).prop("disabled",false);
  		});
    	var name = this.name;
        $("#organform").append("<form action='selectpatientdrug.htm?patientid=" + name + "' method='post' ><input type='submit' name='submit' value='Next' /></form>");
        $(this).prop("disabled",true);   
    });
});
</script>
<title>Select Appointment</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br><br>
<h1>Select Appointment</h1>
<table id="table" border="1">
<tr><td>Patient Name</td><td>Time</td><td>Reservation Number</td></tr>
</table><br>
<div id="pages"></div>
<br>

<div id="organform">
</div><br>

<form action="home.htm" method="get">
    <input type="submit" value="Back to the main page">
</form>
</center>
</body>
</html>