<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.zd.utils.redisutil.fn1.userlogin.UserRedis,com.zd.utils.ZdConstant,redis.clients.jedis.Jedis,java.util.*,java.text.*"%>
<%
	Jedis jedis=new Jedis(ZdConstant.REDIS_URL);
	Date d=new Date();
	DateFormat df=new SimpleDateFormat("yyyyMMdd");
	String action="onLineUserPerDay:"+df.format(d);
	

%>
	<p style="margin-top:20px;margin-left:410px;">用户登入情况查看</p>
	<div style="margin-top:20px;margin-left:350px;border:1px solid #ccc;width:250px;padding:10px;">
		<b>当天在线人数:</b><span id="number"><%=UserRedis.getOnLineUserCount(jedis,action) %></span><br>
		<b>vip用户（连续n天上线）总数:</b><span id="number"><%=UserRedis.getSeriesOnLineInNDays(jedis,2) %></span><br>
		<b>白金用户（n天内曾经上线）总数:</b><span id="number"><%=UserRedis.getOnLineInNDays(jedis,7) %></span>
	</div>