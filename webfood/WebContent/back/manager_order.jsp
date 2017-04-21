<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table id="show_order_info" data-options="fit:true"></table>
<div id="show_order_item_info" class="easyui-dialog"
	data-options="iconCls:'icon-add',resizable:true,fit:true,closed:true">
	
	
	<table id="show_orderitem_info" data-options="fit:true"></table>

</div>
<input type="hidden" id="status_id_update"/>
<script type="text/javascript">
$('#show_order_info').datagrid({    
    url:'../resorder.action',  
    fitColumns:true,
    loadMsg:'数据加载中....',
    queryParams:{op:'findOrder'},
    pagination:true,//显示分页栏
    striped:true,
    nowrap:true,
    rownumbers:true,            
    pageSize:5,
    pageList:[5,10,20,30,40,50],
    sortName:'roid',
    remoteSort:false,
    columns:[[    
		//{field:'roids',title:'订单编号',width:100,align:'center',checkbox:true},  
		{field:'roid',title:'订单编号',width:100,align:'center',sortable:true},  
        {field:'userid',title:'顾客编号',width:100,align:'center',sortable:true},    
        {field:'address',title:'收货地址',width:200,align:'center'},    
        {field:'tel',title:'电话号码',width:100,align:'center'},
        {field:'ordertime',title:'下单时间',width:100,align:'center'},    
        {field:'deliverytime',title:'送达时间',width:200,align:'center'},   
        {field:'ps',title:'备注',width:200,align:'center'},   
        {field:'status',title:'状态',width:200,align:'center'},   
        {field:'kj',title:'操作',width:200,align:'center',formatter:formatOper},    
    ]],
    toolbar:"#"
});



function formatOper(val,row,index){
	var roid=row.roid;
	$("#status_id_update").val(row.status);
	return '<input type="button" onclick="lookitem('+roid+')" value="查看详细" > <br> <input type="button"  onclick="updateStatus('+roid+')" value="修改状态"    >'
}

function lookitem(roid){
	$("#show_order_item_info").dialog({title:'查看订单详情信息',closed:false,iconCls:'icon-edit'});
	$('#show_orderitem_info').datagrid({    
	    url:'../resorder.action',  
	    fitColumns:true,
	    loadMsg:'数据加载中....',
	    queryParams:{op:'findOrderItem',roid:roid},
	    striped:true,
	    nowrap:true,
	    rownumbers:true,            
	    sortName:'roiid',
	    remoteSort:false,
	    columns:[[    
			{field:'roiids',title:'订单详细编号',width:100,align:'center',checkbox:true},  
			{field:'roiid',title:'订单详细编号',width:100,align:'center',sortable:true}, 
			{field:'roid',title:'订单编号',width:100,align:'center',sortable:true}, 
			{field:'fid',title:'商品编号',width:100,align:'center',sortable:true},  
			{field:'fname',title:'商品名称',width:100,align:'center',sortable:true},  
	        {field:'dealprice',title:'成交价',width:100,align:'center',sortable:true},    
	        {field:'num',title:'数量',width:200,align:'center'},    
	    ]]
	});
}

function updateStatus(roid){
	$.ajax({
		url:"../resorder.action",
		data:"op=updateStatus&roid="+roid+"&status="+$("#status_id_update").val(),
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.code==0){
				alert("修改失败....");
			}else{
				alert("修改成功....");
			}
		}	
	});
}


</script>
<div>






</div>