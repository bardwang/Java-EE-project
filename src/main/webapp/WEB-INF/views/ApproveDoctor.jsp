<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        url: "pengingdoctortable.htm",
        success: function(data) {
            var jsondata = JSON.parse(data);
            var tabledata = jsondata.list;
            var pagebegin = jsondata.pagebegin;
            var pageend = jsondata.pageend;
            var flag = 0;
            tabledata.forEach(function(entry) {
            	flag = 1;
            	$("#pages").empty();
                $("#table").append("<tr><td>" + entry.name + "</td>" + 
                					   "<td>" + entry.concentration + "</td>" +
                					   "<td>" + entry.telephone + "</td>" + 
                					   "<td>" + entry.organizationname + "</td>" + 
                					   "<td>" + entry.organizationtype + "</td>" +
                					   "<td>Pending</td>" + 
                					   "<td><button id='approve' accountid='" + entry.accountid + "'>Approve</button></td><tr>");
            });
            if(flag == 0){
            	$("#table").remove();
            	$("#content").append("<p>All the doctor accounts have been approved</p>")
            }
            for (i = pagebegin; i <= pageend; i++) { 
                $("#pages").append("<button id='" + i + "'>" + i + "</button> ");
            }
        }
    });
    
    $(document).on("click", '#approve',function(){
    	var accountid = $(this).attr("accountid");
        $.ajax({
        	cache: false,
            type: "POST",
            url: "approvedoctor.htm?accountid=" + accountid,
            success: function(data) {          	
            	location.reload();
            }
        });
    });
    
    $(document).on("click", 'button',function(){
        if(!isNaN(this.id)){
            cpage = parseInt(this.id);
            $("#table tr").slice(1).remove();
            $.ajax({
            	cache: false,
                type: "POST",
                url: "pengingdoctortable.htm?cpage=" + cpage,
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
			         					   "<td>" + entry.telephone + "</td>" + 
			         					   "<td>" + entry.organizationname + "</td>" +
			         					   "<td>" + entry.organizationtype + "</td>" +
			         					   "<td>Pending</td>" +
                        				   "<td><button id='approve' accountid='" + entry.accountid + "'>Approve</button></td><tr>");
                        for (i = pagebegin; i <= pageend; i++) { 
                            $("#pages").append("<button id='" + i + "'>" + i + "</button> ");
                        }
                    });
                }
            });
        }
    });
});
</script>
<title>Approve Doctor</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br><br>
<h1>Approve Doctor</h1>
<table id="table" border="1">
<tr><td>Name</td><td>Concentration</td><td>Telephone</td><td>Organization Name</td><td>Organization Type</td><td>Status</td></tr>
</table><br>
<div id="content"></div>
<div id="pages"></div>
<br>

<input type="button" value="Back" onclick="window.history.back()" /> 

</center>
</body>
</html>