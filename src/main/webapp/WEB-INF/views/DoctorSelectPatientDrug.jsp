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
        url: "doctordrugtable.htm",
        success: function(data) {
        	debugger;
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
                					   "<td><button id='select' drugid='" + entry.drugid + "' >select</button></td></tr>");
            });
            for (i = pagebegin; i <= pageend; i++) { 
                $("#pages").append("<button id='" + i + "'>" + i + "</button> ");
            }
            var tabledata2 = jsondata.list2;
            var totalprice = "Total Price: " + jsondata.totalprice + "$";
            tabledata2.forEach(function(entry) {
            	debugger;
                $("#cart").append("<tr><td>" + entry.name + "</td>" + 
                					   "<td>" + entry.pharma + "</td>" +
                					   "<td>" + entry.price + "</td>" + 
                					   "<td>" + entry.quantity + "</td>" +
                					   "<td><button id='delete' drugid='" + entry.drugid + "' quantity='" + entry.quantity + "' drugname='" + entry.name + "' >delete</button></td></tr>");
            });
            $("#totalprice").empty();
            $("#totalprice").append(totalprice);
        }
    }); 
    
    $(document).on("click", '#select',function(){
    	$("#organform").empty();
    	$('button').each(function() {
        $(this).prop("disabled",false);
  		});
    	var drugid = $(this).attr("drugid");
        $("#organform").append("Quantity:<input type='text' id='inputquantity' drugid='" + drugid + "' maxlength='5'></input></form>");
        $("#organform").append("<br><br><button id='add'>add</button>");
        $(this).prop("disabled",true);   
    });
    
    
    $(document).on("click", "#add",function(){
    	debugger;
    	var quantity = $("#inputquantity").val();
    	var drugid = $("#inputquantity").attr("drugid");
        $.ajax({
        	cache: false,
            type: "POST",
            url: "addpatientdrug.htm?drugid=" + drugid + "&quantity=" + quantity,
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
    
    $(document).on("click", "#delete",function(){
    	debugger;
    	var drugid = $(this).attr("drugid");
    	var quantity = $(this).attr("quantity");
    	var name = $(this).attr("drugname");
        $.ajax({
        	cache: false,
            type: "POST",
            url: "deletepatientdrug.htm?drugid=" + drugid + "&quantity=" + quantity + "&name=" + name,
            success: function(data) {
            	location.reload();
            }
        });
    });
    
    $(document).on("click", "button",function(){
        if(!isNaN(this.id)){
            cpage = parseInt(this.id);
            $("#table tr").slice(1).remove();
            $("#cart tr").slice(1).remove();
            $("#organform").empty();
            $("#error").empty();
    		$("#quantity").val("");
            $.ajax({
            	cache: false,
                type: "POST",
                url: "doctordrugtable.htm?cpage=" + cpage,
                success: function(data) {
                	debugger;
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
                        					   "<td><button id='select' drugid='" + entry.drugid + "' >select</button></td></tr>");
                    });
                    for (i = pagebegin; i <= pageend; i++) { 
                        $("#pages").append("<button id='" + i + "'>" + i + "</button> ");
                    }
                    var tabledata2 = jsondata.list2;
                    var totalprice = "Total Price: " + jsondata.totalprice + "$";
                    tabledata2.forEach(function(entry) {
                    	debugger;
                        $("#cart").append("<tr><td>" + entry.name + "</td>" + 
                        					   "<td>" + entry.pharma + "</td>" +
                        					   "<td>" + entry.price + "</td>" + 
                        					   "<td>" + entry.quantity + "</td>" +
                        					   "<td><button id='delete' drugid='" + entry.drugid + "' quantity='" + entry.quantity + "' drugname='" + entry.name + "' >delete</button></td></tr>");
                    });
                    $("#totalprice").empty();
                    $("#totalprice").append(totalprice);
                }
            });
        }
    });
});
</script>
<title>Select Patient Drug</title>
</head>
<body BACKGROUND="doctorbackground.jpg"/>
<center>
<h1>Select Patient Drug</h1>
<p>Drug Inventory</p>
<table id="table" border="1">
<tr><td>Drug Name</td><td>Pharma</td><td>Price</td><td>Quantity</td></tr>
</table><br>
<div id="pages"></div>
<br>
<div id="organform"></div><br>
<div id="error" style="color:red"></div><br>

<p>Cart</p>
<table id="cart" border="1">
<tr><td>Drug Name</td><td>Pharma</td><td>Price</td><td>Quantity</td></tr>
</table><br>
<div id="totalprice"></div>

<br>
<form action="doctorfinish.htm">
<input type="submit" name="submit" value="Finish" />
</form>

<br>
<input type="button" value="Back" onclick="window.history.back()" /> 

</center>
</body>
</html>