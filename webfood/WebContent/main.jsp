<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div class="head">
		小萌神订餐网
		<div class="right">
			<span id="showlogin"><a>登录</a></span>
			<span id="exitspan" style="display:none;"></span>
		</div>
	</div>
	<div class="mubu" id="mubu"></div>
	<div class="login" id="login">
		<span id="unshow">X</span>
		<form name="myform" id="myform">
			<input type="hidden" name="op" id="op" value="login">
			<table>
				<tr id="pp">
					<td class="labname"><label for="username">用户名:</label></td>
					<td colspan="2"><input name="username" type="text" placeholder="请输入用户名" id="username" value="a"/></td>
				</tr>
				<tr id="pp2">
					<td class="labname"><label for="pwd">密码:</label></td>
					<td colspan="2"><input type="password" id="pwd" placeholder="请输入密码" name="pwd" value="a" /></td>
				</tr>
				<tr id="pp3">
					<td class="labname"><label for="">验证码:</label></td>
					<td><input type="text" class="yzm" name="valcode" id="yzm" placeholder="请输入验证码" /></td>
					<td><img src="" id="yzm_img"></td>
				</tr>
			</table>
		</form>
		<input type="button" value="login" class="btn" id="btn">
	</div>

	<div class="login" id="myinfo">
		<span id="unshowinfo">X</span>
		<form name="forminfo" id="orderform">
			<input type="hidden" name="op" value="confirmOrder">
			<table>
				<tr>
					<td class="labname"><label for="address">送货地址:</label></td>
					<td><input name="address" type="text" id="address" placeholder="请输入送货地址"  value="我家"/></td>
				</tr>
				<tr>
					<td class="labname"><label for="tel">联系电话:</label></td>
					<td><input type="text" id="tel" placeholder="请输入联系电话" name="tel" value="15367053290" /></td>
				</tr>
				<tr>
					<td class="labname"><label for="deliverytime">送货时间:</label></td>
					<td><input type="text" name="deliverytime" id="deliverytime" placeholder="请输入送货时间" value="2016年10月30日 12:00" /></td>	
				</tr>
				<tr>
					<td class="labname"><label for="ps">附言:</label></td>
					<td><input type="text" id="ps" placeholder="请输入附言" name="ps" value="不要辣"/></td>
				</tr>
			</table>
			
		</form>
		<input type="button" value="submit" class="btn" id="submit">
	</div>


	<div id="content" class="content">
		<ul id="allfoods" class="allfoods">
		</ul>
		<input type="button" value="首页" id="food_sy" class="food_fy">
		<input type="button" value="上一页" id="food_syy" class="food_fy">
		<input type="text" class="food_fy" id="food_dqy" value="1" style="text-align:center;">
		<input type="button" value="下一页" id="food_xyy" class="food_fy">
		<input type="button" value="尾页" id="food_wy" class="food_fy">
	</div>

	
	<span id="showlook" class="showlook"></span>

	<div class="look" id="look">
		<span>浏览记录</span>
		<ul id="ull" class="ull">
			
		</ul>
	</div>
	
	<span id="showcollect" class="showcollect"></span>
	<div class="collect" id="collect">
		<span>我的收藏</span>
		<ul id="collectull" class="collectull">
			
		</ul>
	
	</div>
	<div id="gou" class="gou">
		<div class="carbag" id="carbag">
			<p>购物车<span id="delall">[清空]</span></p>
			<table id="bagcontainer" cellspacing="0" cellpadding="0">
				
			</table>
		</div>
		<span id="foodcount" class="nofood">购物车是空的</span>
		<div class="car" id="car">
			
		</div>
	</div>