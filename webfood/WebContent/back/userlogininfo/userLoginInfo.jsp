<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.zd.utils.redisutil.fn1.userlogin.UserRedis,com.zd.utils.ZdConstant,redis.clients.jedis.Jedis,java.util.*,java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站登录用户数据统计</title>
</head>
<body>
<%
	Jedis jedis=new Jedis(ZdConstant.REDIS_URL);
	Date d=new Date();
	DateFormat df=new SimpleDateFormat("yyyyMMdd");
	String action="onLineUserPerDay:"+df.format(d);
	

%>
	<div>
		<b>当天在线人数:</b><span id="number"><%=UserRedis.getOnLineUserCount(jedis,action) %></span>
		<b>vip用户（连续n天上线）总数:</b><span id="number"><%=UserRedis.getSeriesOnLineInNDays(jedis,2) %></span>
		<b>白金用户（n天内曾经上线）总数:</b><span id="number"><%=UserRedis.getOnLineInNDays(jedis,7) %></span>
	</div>
</body>
</html>