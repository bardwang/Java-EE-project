<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-1.12.1.min.js"></script>
<script>
var cpage = 1;
var organizationname = "";
$(document).ready(function(){
    $.ajax({
    	cache: false,
        type: "POST",
        url: "drugtable.htm",
        success: function(data) {
            var jsondata = JSON.parse(data);
            var tabledata = jsondata.list;
            var pagebegin = jsondata.pagebegin;
            var pageend = jsondata.pageend;
            organizationname = jsondata.organizationname;
            tabledata.forEach(function(entry) {
            	$("#pages").empty();
                $("#table").append("<tr><td>" + entry.name + "</td>" + 
                					   "<td>" + entry.pharma + "</td>" +
                					   "<td>" + entry.price + "</td>" + 
                					   "<td>" + entry.quantity + "</td>" +
                					   "<td><button id='update' drugid='" + entry.drugid + "' value='" + [entry.name, entry.pharma, entry.price, entry.quantity] + "'>update</button></td>" +
                					   "<td><button id='delete' drugid='" + entry.drugid + "'>delete</button></td></tr>");
            });
            for (i = pagebegin; i <= pageend; i++) { 
                $("#pages").append("<button id='" + i + "'>" + i + "</button> ");
            }
        }
    });
    
    $(document).on("click", "#add",function(){
    	var name = $("#name").val();
    	var pharma = $("#pharma").val();
    	var price = $("#price").val();
    	var quantity = $("#quantity").val();
        $.ajax({
        	cache: false,
            type: "POST",
            url: "adddrug.htm?organizationname=" + organizationname + "&name=" + name + "&pharma=" + pharma + "&price=" + price + "&quantity=" + quantity,
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
    
    $(document).on("click", "#updatedrug",function(){
    	debugger;
    	var drugid = $(this).attr("drugid");
    	var name = $("#name").val();
    	var pharma = $("#pharma").val();
    	var price = $("#price").val();
    	var quantity = $("#quantity").val();
        $.ajax({
        	cache: false,
            type: "POST",
            url: "updatedrug.htm?drugid=" + drugid + "&name=" + name + "&pharma=" + pharma + "&price=" + price + "&quantity=" + quantity,
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
    	var drugid = $(this).attr("drugid");
        $.ajax({
        	cache: false,
            type: "POST",
            url: "deletedrug.htm?organizationname=" + organizationname + "&drugid=" + drugid,
            success: function(data) {          	
            	location.reload();
            }
        });
    });
    
    $(document).on("click", '#update',function(){
    	
    	debugger;
    	if($(this).css("color") == "rgb(0, 0, 0)"){
    		var content = $(this).val().split(',');
    		$("#name").val(content[0]);
    		$("#pharma").val(content[1]);
    		$("#price").val(content[2]);
    		$("#quantity").val(content[3]);
    		$("#addupdate").empty();
    		var drugid = $(this).attr("drugid");
    		$("#addupdate").append("<button id='updatedrug' drugid='" + drugid + "'>update</button>");
    		$("button").each(function() {
        		$(this).css("color", "black");
          		});
    		$(this).css("color", "red");
    	}else{   		
    		$("#name").val("");
    		$("#pharma").val("");
    		$("#price").val("");
    		$("#quantity").val("");
    		$("#addupdate").empty();
    		$("#addupdate").append("<button id='add'>add</button>");
    		$(this).css("color", "black");
    	}
    	
    });
    
    $(document).on("click", "button",function(){
        if(!isNaN(this.id)){
            cpage = parseInt(this.id);
            $("#table tr").slice(1).remove();
            $("#error").empty();
            $("#name").val("");
    		$("#pharma").val("");
    		$("#price").val("");
    		$("#quantity").val("");
    		$("#addupdate").empty();
    		$("#addupdate").append("<button id='add'>add</button>");
            $.ajax({
            	cache: false,
                type: "POST",
                url: "drugtable.htm?cpage=" + cpage,
                success: function(data) {
                	debugger;
                    var jsondata = JSON.parse(data);
                    var tabledata = jsondata.list;
                    var pagebegin = jsondata.pagebegin;
                    var pageend = jsondata.pageend;
                    tabledata.forEach(function(entry) {
                    	$("#pages").empty();
                        $("#table").append("<tr><td>" + entry.name + "</td>" + 
				                        	   "<td>" + entry.pharma + "</td>" +
				         					   "<td>" + entry.price + "</td>" + 
				         					   "<td>" + entry.quantity + "</td>" +
				         					  "<td><button id='update' drugid='" + entry.drugid + "' value='" + [entry.name, entry.pharma, entry.price, entry.quantity] + "'>update</button></td>" +
                        					   "<td><button id='delete' drugid='" + entry.drugid + "'>delete</button></td></tr>");
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
<title>Manage Drug</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<h1>Manage Drug in ${requestScope.organizationname}</h1>
<table id="table" border="1">
<tr><td>Drug Name</td><td>Pharma</td><td>Price</td><td>Quantity</td></tr>
</table><br>
<div id="pages"></div>
<br>

Drug Name:
<input type="text" id="name" maxlength='30'></input><br><br>
Pharma:
<input type="text" id="pharma" maxlength='30'></input><br><br>
Price:
<input type="text" id="price" maxlength='5'></input><br><br>
Quantity:
<input type="text" id="quantity" maxlength='5'></input><br><br>

<div id="addupdate">
<button id="add">add</button>
</div>
<br>
<div id="error" style="color:red"></div><br>

<form action="home.htm" method="get">
    <input type="submit" value="Back to the main page">
</form>

</center>
</body>
</html>