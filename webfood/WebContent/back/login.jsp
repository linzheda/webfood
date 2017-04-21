<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
* {
	padding: 0px;
	margin: 0px auto;
	font-family: "微软雅黑";
}

li {
	list-style: none;
}

a {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

body {
	width: 100%;
	background: url(../image/bg.jpg);
	background-size: cover;
}
.login{
	text-align: center;
	border-radius: 14px;
	z-index: 1000;
	width: 30%;
	position: fixed;
	top:20%;
	left: 35%;
	background: rgba(0,0,0,0.75);
}
.login span{
	float: right;
	margin: 10px;
	display: block;
	width: 25px;
	height: 25px;
	line-height: 27px;
	border-radius:50%;
	border: 1px #999 solid; 
	color: #999;
	cursor: pointer;
}

.login span:hover{
	color:red;
}
.login form {
	margin-top: 15px;
	margin-bottom: 0px;
}
.login form tr{
	display: block;
	margin: 20px 0 0 30px;
	color: #ccc;
	width: 85%;
	border-bottom:1px solid #666;
}

.labname{
	width: 80px;
}
.login form .yzm{
	width: 130px;
}
.login .btn{
	font-size:30px; 
	line-height: 60px;
	color: #fff;
	width: 280px;
	height: 60px;
	border-radius: 14px;
	background: #3ea751;
	margin-top: 10px;
	padding: 0px;
	border: 0;
	margin-top:25px;
	cursor: pointer;
}
.login form tr input{
	width: 200px;
	height: 30px;
	margin: 10px;
	border: 0;
	background: none;
	padding:5px;
	color: #fff; 
}
.login form tr #yzm_img{
	width: 100px;
	height: 40px;
	cursor: pointer;
}
.mubu{
	position: fixed;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
	background-color:rgba(0,0,0,0.2);
	z-index: 100;
}

</style>
<title>管理员登录</title>
<link rel="shortcut icon" href="../image/eat0.ico">
</head>
<body>
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");

%>
	<div class="mubu" id="mubu"></div>
	<div class="login" id="login">
		<form name="myform" id="myform" action="../resadmin.action">
			<input type="hidden" name="op" id="op" value="adminlogin" >
			<table>
				<tr id="pp">
					<td class="labname"><label for="username">用户名:</label></td>
					<td colspan="2"><input name="raname" type="text"
						placeholder="请输入用户名" id="username" value="a" /></td>
				</tr>
				<tr id="pp2">
					<td class="labname"><label for="pwd">密&nbsp;&nbsp;&nbsp;码:</label></td>
					<td colspan="2"><input type="password" id="pwd"
						placeholder="请输入密码" name="rapwd" value="a" /></td>
				</tr>
				<tr id="pp3">
					<td class="labname"><label for="">验证码:</label></td>
					<td><input type="text" class="yzm" name="valcode" id="yzm" placeholder="请输入验证码" /></td>
					<td><img src="" id="yzm_img"></td>
				</tr>
				<tr>
					<td><input type="submit" value="login" class="btn" id="btn"></td>
				</tr>
			</table>
		</form>

	</div>
</body>
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(function(){
	$("#yzm_img").attr("src","../verifyCode.action?"+new Date().getTime());
	$("#yzm_img").click(function(){//点击验证码刷新
		$(this).attr("src","../verifyCode.action?"+new Date().getTime());
	});
});
</script>
</html>