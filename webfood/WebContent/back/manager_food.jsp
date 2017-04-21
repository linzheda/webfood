<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table id="show_food_info" data-options="fit:true"></table>

<div id="food_manager_search" style="height: 28px;">
	
	<div class="datagrid-btn-separator"></div>
	<a href="javascript:updatefoodInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" 
			style="float: left">修改</a>
	<div class="datagrid-btn-separator"></div>
	
	<select id="search_type">
		<option value="fname">菜的名称</option>
		<option value="fid">菜的编号</option>
		<option value="detail">菜的描述</option>
		<option value="normprice">菜的原价</option>
		<option value="realprice">菜的现价</option>
	</select>
	<label>菜品名称：</label><input type="text" name="gname" id="search_content">
	<a href="javascript:food_search()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="float: right;margin-right:700px; ">查询</a>
	
</div>
<div id="show_foods_update_info" class="easyui-dialog"
	data-options="iconCls:'icon-add',resizable:true,fit:true,closed:true">
	
	<form  method="post" >
		<table>
			<tr>
				<td>菜品名称：</td>
				<td>
					<input type="text" placeholder="请输入菜名" name="manager_fname" id="manager_fname" >
					<input type="hidden" name="manager_fid" id="manager_fid" >
					<span>只能输入字母或数字，4-16字符</span>
				</td>
			</tr>
			<tr>
				<td>菜品原价：</td>
				<td>
					<input type="number" placeholder="请输入原价" name="manager_normprice" id="manager_normprice" >
				</td>
			</tr>
			<tr>
				<td>菜品现价：</td>
				<td>
					<input type="number" placeholder="请输入现价" name="manager_realprice" id="manager_realprice" >
				</td>
			</tr>
			<tr>
				<td>菜品描述</td>
				<td>
					<input type="text"  name="manager_detail" id="manager_detail" >
				</td>
			</tr>
			<tr >
				<td>菜品图片：</td>
				<td><input type="file"  name="manager_fphoto" id="manager_fphoto" multiple="multiple" onchange="previewMultipleImage(this,'manager_showpic')">
				<span></span></td>
			</tr>
			<tr style="margin-top:20px;">
				<td></td>
				<td>
					<div id="manager_showpic"  ></div>
				</td>
			</tr>
			
			<tr>
				<td>
					<input type="button" value="修改" onclick="manager_update()"  >
				</td>
			</tr>
			
		</table>
		
	</form>
	
	
	
	
</div>
<input type="hidden" id="detail_id_upordown"/>
<script type="text/javascript">
	$('#show_food_info').datagrid({    
	    url:'../resfood.action',  
	    fitColumns:true,
	    loadMsg:'数据加载中....',
	    queryParams:{op:'findFoods'},
	    pagination:true,//显示分页栏
	    striped:true,
	    nowrap:true,
	    rownumbers:true,
	    pageSize:5,
	    pageList:[5,10,20,30,40,50],
	    sortName:'fid',
	    remoteSort:false,
	    columns:[[    
			{field:'fids',title:'菜品编号',width:100,align:'center',checkbox:true},  
	        {field:'fphoto',title:'菜品图片',width:100,align:'center',formatter: function(value,row,index){
	        	var picStr="";
				if (value.indexOf(",")>0){
					value=value.split(",");
					
					for(var i=0;i<value.length;i++){
						picStr+="<img src='../image/foods/"+value[i]+"' width='100px' height='100px' />";
					}
					
				} else if(value!=""){
					picStr+="<img src='../image/foods/"+value+"' width='100px' height='100px' />";
				}else{
					picStr+="<img src='../image/line.png' width='100px' height='100px' />";
				}
				return picStr;
			}},    
	        {field:'fid',title:'菜品编号',width:100,align:'center',sortable:true},    
	        {field:'fname',title:'菜品名称',width:200,align:'center'},    
	        {field:'normprice',title:'菜品原价',width:100,align:'center'},
	        {field:'realprice',title:'菜品现价',width:100,align:'center'},    
	        {field:'detail',title:'菜品描述',width:200,align:'center'},   
	        {field:'kj',title:'操作',width:200,align:'center',formatter:formatOper},    
	    ]],
	    toolbar:"#food_manager_search"
	});
	
	

function formatOper(val,row,index){
	var fid=row.fid;
	$("#detail_id_upordown").val(row.detail);
	return '<input type="button" onclick="editFoodUp('+fid+')" value="上架" ><br><input type="button" onclick="editFoodDown('+fid+')" value="下架" >';
	
}
function updatefoodInfo(){
	var rows=$("#show_food_info").datagrid("getChecked");
	if(rows.length>1||rows.length<=0){
		$.messager.alert("温馨提示","请选中一行数据进行修改....","error");
	}else{
		rows=rows[0];
		$("#show_foods_update_info").dialog({title:'修改菜品信息',closed:false,iconCls:'icon-edit'});
		$("#manager_fname").val(rows.fname);
		$("#manager_normprice").val(rows.normprice);
		$("#manager_realprice").val(rows.realprice);
		$("#manager_detail").val(rows.detail);
		$("#manager_fid").val(rows.fid);
	}
}	
function manager_update(){
	$.ajax({
		url:"../resfood.action",
		data:"op=updateFood&fname="+$("#manager_fname").val()+"&normprice="+$("#manager_normprice").val()+"&realprice="+$("#manager_realprice").val()+"&fphoto="+$("#manager_fphoto").val()+"&detail="+$("#manager_detail").val()+"&fid="+$("#manager_fid").val(),
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.code==0){
				alert("修改失败....");
			}else{
				alert("修改成功....")
			}
		}	
	});
}	
function editFoodUp(fid){
	$.ajax({
		url:"../resfood.action",
		data:"op=upOrDown&fid="+fid+"&detailps=up&detail="+$("#detail_id_upordown").val(),
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.code==0){
				alert("修改失败....");
			}else{
				alert("修改成功....");
				//window.location.reload();
			}
		}	
	});
	
}	
function editFoodDown(fid){
	$.ajax({
		url:"../resfood.action",
		data:"op=upOrDown&fid="+fid+"&detailps=down&detail="+$("#detail_id_upordown").val(),
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.code==0){
				alert("修改失败....");
			}else{
				alert("修改成功....");
				//window.location.reload();
			}
		}	
	});
}		

function food_search(){
	$('#show_food_info').datagrid({    
	    url:'../resfood.action',  
	    fitColumns:true,
	    loadMsg:'数据加载中....',
	    queryParams:{op:'searchFoods',type:$("#search_type").val(),content:$("#search_content").val()},
	    pagination:true,//显示分页栏
	    striped:true,
	    nowrap:true,
	    rownumbers:true,
	   	pageSize:5,
	  	pageList:[5,10,20,30,40,50],
	    sortName:'fid',
	    remoteSort:false,
	    columns:[[    
			{field:'fids',title:'菜品编号',width:100,align:'center',checkbox:true},  
	        {field:'fphoto',title:'菜品图片',width:100,align:'center',formatter: function(value,row,index){
	        	var picStr="";
				if (value.indexOf(",")>0){
					value=value.split(",");
					
					for(var i=0;i<value.length;i++){
						picStr+="<img src='../image/foods/"+value[i]+"' width='100px' height='100px' />";
					}
					
				} else if(value!=""){
					picStr+="<img src='../image/foods/"+value+"' width='100px' height='100px' />";
				}else{
					picStr+="<img src='../image/line.png' width='100px' height='100px' />";
				}
				return picStr;
			}},    
	        {field:'fid',title:'菜品编号',width:100,align:'center',sortable:true},    
	        {field:'fname',title:'菜品名称',width:200,align:'center'},    
	        {field:'normprice',title:'菜品原价',width:100,align:'center'},
	        {field:'realprice',title:'菜品现价',width:100,align:'center'},    
	        {field:'detail',title:'菜品描述',width:200,align:'center'},   
	        {field:'kj',title:'操作',width:200,align:'center',formatter:formatOper},    
	    ]],
	    toolbar:"#food_manager_search"
	});
}
	
	
	
	
	
</script>












