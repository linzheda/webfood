<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.zd.utils.redisutil.fn2.rank.RankUtil,com.zd.utils.ZdConstant,redis.clients.jedis.Jedis,java.util.*,java.text.*"%>

	<div style="margin-left:350px;margin-top:20px;">
		<p style="padding-left:30px;">查看用户排名</p>
		<input type="text" name="text"  placeholder="请输入查询前n名用户" id="topn"  >
		<input type="button" onclick="search()" value="查询" >
	</div>
	
	
	<div id="showrank" style="margin-left:350px;margin-top:20px;border:1px solid #ccc;width:200px;">
		
	</div>
	<script>
		
		function search(){
			var topn=$("#topn").val();
			$.ajax({
				url:"../resuser.action",
				data:"op=findRank&topn="+topn,
				type:"POST",
				dataType:"json",
				success:function(data){
					showrank(data);
				}
			});
		}
		function showrank(data){
			var obj=data;
			var str="";
			var count=1;
			for(var i in obj){
				str+="第"+count +"名的用户ID："+"<label style='border:1px solid #ccc;'>"+obj[i].uid+"</label><br>";
			}
			$("#showrank").html(str);
		}
	
	</script>
	
	
