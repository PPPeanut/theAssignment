<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>华师抢票</title>
<script type="text/javascript" src="js/Calendar3.js"></script>
</head>
<body>
	<h1>登录成功</h1>
	<form action="inquiry/ticket" method="get">
	<div id="con">
		<label>出发地:</label>
		<select name="start">
			<option value="GZQ">广州</option>
			<option value="">1</option>
			<option value="">1</option>
			<option value="">1</option>
			<option value="">1</option>
		</select>
		<label>目的地:</label>
		<select name="end">
			<option value="SHH">上海</option>
			<option value="">1</option>
			<option value="">1</option>
			<option value="">1</option>
			<option value="">1</option>
		</select>
		<label>出发日期:</label>
		<input name="time" type="text" id="control_date" size="10" maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" />
		<input type="submit" value="查询"/>
	</div>
	</form>
	

	
	<script>
	onload= function(){

		var table = document.createElement("table");

	    table.setAttribute("border","border");
	    var tbody = document.createElement("tbody");
	    table.appendChild(tbody);	
	    var tr = tbody.insertRow (0);	
		var str = "编号,出发地,目的地,出发时间,到达时间,是否有余票,是否监控".split(",");
	    for (var i = 0; i < str.length; i++)
	    {
	        var th = document.createElement("th");
	        th.setAttribute("style","width:100px");
	        th.innerHTML = str[i];
	        tr.appendChild(th);
	    }
		document.body.appendChild(table);
		
		<%
		ArrayList<List<String>> all=(ArrayList<List<String>>)request.getSession().getAttribute("stationMessage");
 		if(all!=null){
			for (int i=0;i<all.size();i++){
		%>		
				var form=document.createElement('form');
				form.setAttribute("method","get");
				form.setAttribute("action","monitor/Ticket");
				var table =document.createElement('table');
				form.appendChild(table);
				table.setAttribute("border","border");
				
				var tr = table.insertRow (table.rows.length);

		<%
				for (String string : all.get(i)){
		%>
					var td = tr.insertCell(tr.cells.length);
					var input = document.createElement('input');
					input.setAttribute("style","width:97px");
					input.setAttribute("name","<%=i%>");
					input.setAttribute("value","<%=string%>")
					td.appendChild(input);
					
		<%
				}
		%>
				 var td = tr.insertCell(tr.cells.length);
				 var input = document.createElement('input');
				 input.setAttribute("style","width:97px");
				 input.setAttribute("type","submit");
				 input.setAttribute("value","监测余票");
				 td.appendChild(input);	
				 
				 document.body.appendChild(form);
		<%
			}
		}
		%>   
	}
	</script>
	
	<label id="tip">提示:请选择要购买的车票的信息</label>
</body>
</html>