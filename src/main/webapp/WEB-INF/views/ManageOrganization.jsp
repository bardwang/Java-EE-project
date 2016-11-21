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
        url: "organizationtable.htm",
        success: function(data) {
            var jsondata = JSON.parse(data);
            var tabledata = jsondata.list;
            var pagebegin = jsondata.pagebegin;
            var pageend = jsondata.pageend;
            tabledata.forEach(function(entry) {
            	$("#pages").empty();
                $("#table").append("<tr><td>" + entry.name + "</td>" + 
                					   "<td><button id='delete' name='" + entry.name + "'>delete</button></td><tr>");
            });
            for (i = pagebegin; i <= pageend; i++) { 
                $("#pages").append("<button id='" + i + "'>" + i + "</button> ");
            }
        }
    });
    
    $("#add").click(function(){
    	var name = $("#name").val();
    	var type = $("#type").val();
    	var city = $("#city").val();
    	var capacity = $("#capacity").val();
        $.ajax({
        	cache: false,
            type: "POST",
            url: "organizationadd.htm?name=" + name + "&type=" + type + "&city=" + city,
            success: function(data) {
            	if(data != ""){
            	var jsondata = JSON.parse(data);
                var error = jsondata.error;
                $("#error").empty();
            	$("#error").append(error);
                }else{
            	location.reload();
                }
            }
        });
    });
    
    $(document).on("click", '#delete',function(){
    	debugger;
    	var name = this.name;
        $.ajax({
        	cache: false,
            type: "POST",
            url: "organizationdelete.htm?name=" + name,
            success: function(data) {          	
            	location.reload();
            }
        });
    });
    
    $(document).on("click", 'button',function(){
        if(!isNaN(this.id)){
            cpage = parseInt(this.id);
            $("#table tr").slice(1).remove();
            $("#error").empty();
            $.ajax({
            	cache: false,
                type: "POST",
                url: "organizationtable.htm?cpage=" + cpage,
                success: function(data) {
                	debugger;
                    var jsondata = JSON.parse(data);
                    var tabledata = jsondata.list;
                    var pagebegin = jsondata.pagebegin;
                    var pageend = jsondata.pageend;
                    tabledata.forEach(function(entry) {
                    	$("#pages").empty();
                        $("#table").append("<tr><td>" + entry.name + "</td>" + 
                        		"<td><button id='delete' name='" + entry.name + "'>delete</button></td><tr>");
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
<title>Manage Organization</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<br><br><br>
<h1>Manage ${sessionScope.type} in ${sessionScope.city}</h1>
<table id="table" border="1">
<tr><td>${sessionScope.type} Name</td></tr>
</table><br>
<div id="pages"></div>
<br>

${sessionScope.type} Name:
<input type="text" id="name" maxlength='30'></input>
<input type="hidden" id="type" value="${sessionScope.type}"/>
<input type="hidden" id="city" value="${sessionScope.city}"/>
 
<button id="add">add</button><br><br>
<div id="error" style="color:red"></div><br>

<input type="button" value="Back" onclick="window.history.back()" /> 

</center>
</body>
</html>