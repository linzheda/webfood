<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.zd.utils.redisutil.fn2.rank.RankUtil,com.zd.utils.ZdConstant,redis.clients.jedis.Jedis,java.util.*,java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		int topN=3;
		Jedis jedis=new Jedis(ZdConstant.REDIS_URL);
		Set<String> set=RankUtil.getTopN(jedis,topN);
	%>
	前<%=topN %>名为：
	<%
		for(String uid:set){
			%>
			
				<%=uid %> <br>
			
			<%
		}
	
	
	%>
	
	
</body>
</html>