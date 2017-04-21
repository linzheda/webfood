<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="register" style="margin-left:250px;margin-top:30px;">
	<form  method="post" >
		<table>
			<tr>
				<td>用户名：</td>
				<td>
					<input type="text" placeholder="请输入用户名" name="register_name" id="register_name">
					<span>只能输入字母或数字，4-16字符</span>
				</td>
			</tr>
			<tr>
				<td>密     码：</td>
				<td>
					<input type="password" placeholder="请输入密码" name="register_pwd" id="register_pwd">
					<span>密码长度6-12位</span>
				</td>
			</tr>
			<tr>
				<td>确认密码：</td>
				<td>
					<input type="password" placeholder="请再次输入密码">
				</td>
			</tr>
			<tr>
				<td>性别：</td>
				<td>
					<input type="radio" name="sex" value="男">男 &nbsp;&nbsp;
					<input type="radio" name="sex" value="女">女
				</td>
			</tr>
			<tr>
				<td>电子邮件地址：</td>
				<td><input type="text" placeholder="请输入Email地址" name="register_mail" id="register_mail">
				<span>输入正确的Email地址</span></td>
			</tr>
			<tr>
				<td>兴趣爱好:</td>
				<td>
					<input type="checkbox" value="体育" name="lovely">体育 
					<input type="checkbox" value="音乐" name="lovely">音乐
					<input type="checkbox" value="读书" name="lovely">读书 
					<input type="checkbox" value="旅游" name="lovely">旅游
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="button" value="同意以下协议条款并提交" class="btn" id="btn">
				</td>
			</tr>
			
		</table>
		<div style="width:400px; height:80px;border: solid 2px black;">
			从前有做山，山里有座庙，庙里有个小和尚和一个老和尚，老和尚对小和尚说从前有做山，山里有座庙，庙里有个小和尚和一个老和尚，老和尚对小和尚说...
				欢迎来到小萌神，为了您的账号安全，请接收邮箱并激活....
		</div>
	</form>
</div>
<script>
	$("#btn").click(function(){
		
		$.ajax({
			url:"../resuser.action",
			data:"op=register&register_name="+$("#register_name").val()+"&register_pwd="+$("#register_pwd").val()+"&register_mail="+$("#register_mail").val(),
			type:"POST",
			dataType:"JSON",
			success:function(data){
				if(data.code==1){
					alert(data.obj);
				}else{
					alert(data.Emage);
				}
			}	
		});
		
		
		
	});




</script>