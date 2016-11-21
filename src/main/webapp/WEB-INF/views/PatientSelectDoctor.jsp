<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-1.12.1.min.js"></script>
<script>
var cpage = 1;
$(document).ready(function(){
    $.ajax({
    	cache: false,
        type: "POST",
        url: "doctortable.htm",
        success: function(data) {
        debugger;
            var jsondata = JSON.parse(data);
            var tabledata = jsondata.list;
            var pagebegin = jsondata.pagebegin;
            var pageend = jsondata.pageend;
            tabledata.forEach(function(entry) {
            	$("#pages").empty();
                $("#table").append("<tr><td>" + entry.name + "</td>" + 
                					   "<td>" + entry.concentration + "</td>" +
                					   "<td><button id='select' accountid='" + entry.accountid + "'>select</button></td><tr>");
            });
            for (i = pagebegin; i <= pageend; i++) { 
                    $("#pages").append("<button id='" + i + "'>" + i + "</button> ");
            }
        }
    });
    
    $(document).on("click", '#select',function(){
    	$("#organform").empty();
    	$('button').each(function() {
        $(this).prop("disabled",false);
  		});
    	var accountid = $(this).attr("accountid");
        $("#organform").append("<form action='timesheet.htm?accountid=" + accountid + "' method='post'><input type='submit' name='submit' value='Next' /></form>");
        $(this).prop("disabled",true);   
    });
    
    $(document).on("click", 'button',function(){
        if(!isNaN(this.id)){
            cpage = parseInt(this.id);
            $("#table tr").slice(1).remove();
            $("#organform").empty();
            $.ajax({
            	cache: false,
                type: "POST",
                url: "doctortable.htm?cpage=" + cpage,
                success: function(data) {
                	debugger;
                    var jsondata = JSON.parse(data);
                    var tabledata = jsondata.list;
                    var pagebegin = jsondata.pagebegin;
                    var pageend = jsondata.pageend;
                    tabledata.forEach(function(entry) {
                    	$("#pages").empty();
                    	$("#table").append("<tr><td>" + entry.name + "</td>" + 
                    					   "<td>" + entry.name + "</td>" +
         					   			   "<td><button id='select' accountid='" + entry.accountid + "'>select</button></td><tr>");
                    });
                    for (i = pagebegin; i <= pageend; i++) { 
                            $("#pages").append("<button id='" + i + "'>" + i + "</button> ");
                        }
                }
            });
        }
    });
});
</script>
<title>Patient Select Doctor</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br><br>
<h1>Select Doctor</h1>
<table id="table" border="1">
<tr><td>Doctor Name</td><td>Concentration</td></tr>
</table><br>
<div id="pages"></div>
<br>

<div id="organform">
</div><br>

<input type="button" value="Back" onclick="window.history.back()" />
</center>
</body>
</html>