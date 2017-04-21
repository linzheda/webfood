<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
    %>
<div style="width:85%;margin-top:20px;margin-left:100px;"><br><br>
	菜品名称:<input type="text" name="fname" id="add_fname"/><br><br>
	菜品原价:<input type="number" name="normprice" id="add_normprice"/><br><br>
	菜品现价:<input type="number" name="realprice" id="add_realprice"/><br><br>
	菜品图片:<input type="file" name="fphoto" id="add_fphoto" multiple="multiple" onchange="previewMultipleImage(this,'add_showpic')"/>
	<div id="add_showpic" style="width:80%;" ></div>
	
	
	<p style="clear:both;">菜品描述：</p>
	<div style="float:right">
		 <script id="editor" type="text/plain" style="width:80%;height:150px;"></script>
	</div>
	<br>
	<input type="button" value="添加" id="add_food_addbtn" style="margin-left:200px;width:200px;height:50px;border-radius:8px;font:'楷体';font-size:30px;background:#3ea751;" /><br>
<script type="text/javascript">
var ue = UE.getEditor('editor');
	
		
</script>



</div>
<script type="text/javascript">
$(function(){
	
	
$("#add_food_addbtn").click(function(){
		var fname= $.trim($("#add_fname").val());
		var normprice=$.trim($("#add_normprice").val());
		var realprice=$.trim($("#add_realprice").val());
		var fphoto=$("#add_fphoto").val();
		
		
		$.ajaxFileUpload({
			url:"../resfood.action?op=addFood&fphoto="+fphoto,
			secureuri:false,
			fileElementId:"add_fphoto",
			data:{fname:fname,normprice:normprice,realprice:realprice,detail:ue.getContent(),fphoto:fphoto},
			dataType:"JSON",
			success:function(data,status){
				data=$.trim(data);
				if(data=="0"){
					alert("菜品信息添加失败...");
				}else{
					$("#fname").val("");
					$("#normprice").val("");
					$("#realprice").val("");
					$("#fphoto").val("");
					$("#showpic").html("");
					ue.setContent("");
					alert("菜品信息添加成功.....");
				}
			},
			error:function(data,status,e){
				alert(e);
			}
		});
		
		
		
	});
	
	
	
	

	
})
	



</script>





















