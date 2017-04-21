<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员界面</title>
<link rel="shortcut icon" href="../image/eat0.ico">
<link rel="stylesheet" type="text/css" href="../easyui/css/easyui.css">
<link rel="stylesheet" type="text/css" href="../easyui/css/icon.css">
<link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
<script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="../js/jscharts.js"></script>
<script type="text/javascript" src="../easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../easyui/js/easyui-lang-zh_CN.js"></script>
<script language="javascript" type="text/javascript" src="../datetool/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../easyui/js/admin.js"></script>



<script type="text/javascript" src="../js/showpic.js"></script>
<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
<script type="text/javascript" charset="utf-8"
	src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="../ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="../ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:true" style="height: 80px;background:url(../image/1.gif) #E6EDF4; background-position:center;" >
		
		<marquee direction="left" behavior ="scroll" loop="-1" width="35%" height="100%" onmouseover="this.stop()" onmouseout="this.start()" ><img src="../image/50000.jpg"></marquee>
		
		<marquee direction="right" behavior ="scroll" loop="-1" width="35%" height="100%" onmouseover="this.stop()" onmouseout="this.start()" style="float:right;" ><img src="../image/50000.jpg"></marquee> 
	</div>
	<div data-options="region:'west',split:true,title:'导航'" 
		style="width:200px;">
		<div id="menu_admin" class="easyui-accordion" data-options="fit:true" style="height:400px;" >
			<div title="用户管理"  data-options="iconCls:'icon-menupic'" fit:true style="overflow: auto;">
				<ul id="menu_tree1_admin" class="easyui-tree">  
					 <li id="register_user" >  
                       		<span>用户注册 </span> 
                    </li>  
                    <li id="manager_user">  
                        <span>用户管理</span>  
                    </li> 
                    <li id="online_user_manager">  
                        <span>用户在线查看</span>  
                    </li> 
                    <li id="rank_user_manager">  
                        <span>用户等级查看</span>  
                    </li> 
				</ul>
			</div>
			<div title="菜单管理" fit:true data-options="iconCls:'icon-menupic',selected:true" >
				<ul id="menu_tree2_admin" class="easyui-tree">  
					 <li id="add_menu">  
                        <span>菜谱添加</span>  
                    </li>  
                    <li id="manager_menu">  
                        <span>菜谱管理</span>  
                    </li>  
				</ul>
			</div>
			<div title="订单管理" fit:true data-options="iconCls:'icon-menupic',selected:false" >
				<ul id="menu_tree3_admin" class="easyui-tree">  
					 <li id="manager_order">  
                        <span>订单管理</span>  
                    </li>
                     <li id="order_Report">  
                        <span>订单报表</span>  
                    </li>    
				</ul>
			
			</div>
		</div>
	</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'帮助'"
		style="width: 100px; padding: 10px;">帮助区</div>
	<div data-options="region:'south',split:true" style="height: 50px; background: #A9FACD; padding: 10px;padding-left:50px;">
			Copyright &copy; 2016 Xiaomengsheng Incorporated Company. All rights reserved.<br />
				订餐，就上小萌神订餐网! 
	</div>
	<div
		data-options="region:'center',title:'操作',tools:[{
		iconCls:'icon-full',
		handler:function(){
			full();
		}
	},{
		iconCls:'icon-unfull',
		handler:function(){
			unFull();
		}
	}]">
		<div id="admin_content_info" class="easyui-tabs"
			data-options="fit:true"></div>

	</div>
</body>
</html>
