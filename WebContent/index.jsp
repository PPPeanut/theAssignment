<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ticket.Client"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>华师抢票</title>
<script>

function addDot(){
	document.getElementById('a').value+=(event.offsetX+","+event.offsetY+",");
}
	
function isEmpty(){   
    if(document.getElementById('userN').value==""){   
        alert("用户名不能为空!");           
        return false;   
    }   
    if(document.getElementById('passW').value==""){   
        alert("密码不能为空!");          
        return false;   
    }  
    if(document.getElementById('a').value==""){   
        alert("验证码不能为空!");          
        return false;   
    }  
    return true;
}

	
</script>

</head>
<body>


	<h1>抢枪抢</h1>
	<form action="loginservlet" method="post" name="form1">
	<div>
		<label>账号:</label>
		<input type="text" id='userN' name="username"/>
	</div>
	<div>
		<label>密码:</label>
		
		<input type="password" id='passW' name="password"/>
	</div>
	<input type="hidden" id="a" name="a"/>
	<input type="submit" value="登录" onclick="isEmpty()"/>
	<input type="button" value="刷新" onclick="reload()" />
	
	</form>
	<img src="<%=request.getContextPath()%>/imageservlet" id="photo" onmousedown="addDot(this)" alt="验证码"/>
	<br/>
	<br/>
	<br/>
	<br/>
	<script> 
		function reload(){
			window.location.reload(); 
		}
	</script>
</body>
</html>