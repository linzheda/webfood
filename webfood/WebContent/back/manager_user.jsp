<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table id="show_user_info" data-options="fit:true"></table>
<script type="text/javascript">
var datagridObj;
var editRow = undefined;
var op;
var flag;


datagridObj =$('#show_user_info').datagrid({    
	    url:'../resuser.action',  
	    fitColumns:true,
	    loadMsg:'数据加载中....',
	    queryParams:{op:'userShow'},
	    pagination:true,//显示分页栏
	    striped:true,
	    nowrap:true,
	    rownumbers:true,
	    pageSize:5,
	    pageList:[5,10,20,30,40,50],
	    sortName:'userid',
	    remoteSort:false,
	    columns:[[    
			{field:'userids',title:'用户编号',width:100,align:'center',checkbox:true},  
	        {field:'userid',title:'用户编号',width:100,align:'center',sortable:true},    
	        {field:'username',title:'用户名称',width:200,align:'center',editor:{type:"text",options:{requires:true}}},    
	        {field:'email',title:'电子邮箱',width:100,align:'center',editor:{type:"text",options:{requires:true}}},
	    ]],
	    toolbar: [ {
			iconCls : 'icon-edit',
			text : "修改",
			handler : function() {
				op="updateUserInfo";
				flag="修改";
				//获取用户选中的要修改的那一行
				var rows=datagridObj.datagrid("getChecked")[0];
				if(rows==undefined){
					$.messager.show({title:'温馨提示',msg:'请选择您要你修改的数据....',timeout:2000,showType:'slide'});
				}else{
					if(editRow!=undefined){
						//放弃当前操作
						datagridObj.datagrid("rejectChanges");
						datagridObj.datagrid("endEdit",editRow);	
					}
					
					var index=datagridObj.datagrid("getRowIndex",rows);
					datagridObj.datagrid("updateRow",{index:index,row:rows});
					datagridObj.datagrid('beginEdit',index);
					editRow=index;
				}

			}
		}, '-', {
			iconCls : 'icon-save',
			text : "保存",
			handler : function() {
				datagridObj.datagrid("endEdit",editRow);//结束编辑
				//要获取被编辑的数据
				var rows=datagridObj.datagrid("getChanges")[0];
				
				//发请求到数据库更新
				if(rows==undefined){//说明用户没有进行任何的修改
					datagridObj.datagrid("rejectChanges");
					datagridObj.datagrid("unselectAll");//取消所有的选中
					editRow=undefined;
				}else{
					rows["op"]=op;
					$.post("../resuser.action",rows,function(data){
						data=$.trim(data);
						if(data=="1"){
							$.messager.show({title:flag+'提示',msg:flag+'用户信息成功....',timeout:2000,showType:'slide'});
							datagridObj.datagrid("rejectChanges");
							datagridObj.datagrid("unselectAll");//取消所有选中
							editRow=undefined;
							rows==undefined;
							//重新加载数据
							datagridObj.datagrid("reload");

						}else{
							$.messager.alert('失败提示',flag+'用户信息失败....','error');
						}
					});
				}

			}
		}, '-', {
			iconCls : 'icon-undo',
			text : "撤销",
			handler : function() {
				datagridObj.datagrid("rejectChanges");
				datagridObj.datagrid("endEdit",editRow);
				datagridObj.datagrid("unselectAll");//取消所有的选中
				editRow=undefined;
			}
		}]
	});



	
	
	
</script>