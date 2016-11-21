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
        url: "selecttime.htm",
        success: function(data) {
        	debugger;
            var jsondata = JSON.parse(data);
            $("#table").append("<tr><td>9:00-10:00</td><td>" + transfer(jsondata.time1, 9) + "</td></tr>" + 
            				   "<tr><td>10:00-11:00</td><td>" + transfer(jsondata.time2, 10) + "</td></tr>" +
            				   "<tr><td>11:00-12:00</td><td>" + transfer(jsondata.time3, 11) + "</td></tr>" + 
            				   "<tr><td>13:00-14:00</td><td>" + transfer(jsondata.time4, 13) + "</td></tr>" + 
            				   "<tr><td>14:00-15:00</td><td>" + transfer(jsondata.time5, 14) + "</td></tr>" + 
            				   "<tr><td>15:00-16:00</td><td>" + transfer(jsondata.time6, 15) + "</td></tr>" + 
            				   "<tr><td>16:00-17:00</td><td>" + transfer(jsondata.time7, 16) + "</td></tr>");
        }
    });
    
    $(document).on("click", '#select',function(){
    	$("#timeform").empty();
    	$('button').each(function() {
        $(this).prop("disabled",false);
  		});
    	var time = $(this).attr("time");
        $("#timeform").append("<form action='updatetimesheet.htm?time=" + time + "' method='post'><input type='submit' name='submit' value='Finish' /></form>");
        $(this).prop("disabled",true);   
    });
    
});
    
function transfer(status, id){
	if(status == 0){
		return "<button id='select' time='" + id + "'>click to select</button>";
	}else{
		return "";
	}
}    
</script>
<title>Timesheet</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<h1>Timesheet</h1>
Doctor Name:${requestScope.doctoraccount.name }<br>
Telephone:${requestScope.doctoraccount.telephone }<br>
Year of Experience:${requestScope.doctoraccount.yearsofexperience }<br>
Background:${requestScope.doctoraccount.background }<br>
<p>Please select the time for you appointment</p>
<table id="table" border="1">
</table>
<br>
<div id="timeform">
</div><br><br><br>
<input type="button" value="Back" onclick="window.history.back()" />
</center>
</body>
</html>