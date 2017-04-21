$(function(){
	$('#admin_content_info').tabs('add',{    
		title:"小萌订餐",    
		href:'yc.html',
		fit:true,
		closable:true,    
	});
	$('#menu_tree1_admin').tree({
		onClick: function(node){
			var id=node.id;//获取点击的节点id
			var title="小萌订餐";
			var href="yc.html";
			var tabObj=$('#admin_content_info');
			if(id=="register_user"){//用户注册
				if(tabObj.tabs('exists','用户注册')){
					tabObj.tabs('select','用户注册');
					return;
				}else{
					title="用户注册";
					href='register_user.jsp';
				}

			}else if(id=="manager_user"){//用户管理
				if(tabObj.tabs('exists','用户管理')){
					tabObj.tabs('select','用户管理');
					return;
				}else{
					title="用户管理";
					href='manager_user.jsp';
				}
			}else if(id=="online_user_manager"){//用户在线管理
				if(tabObj.tabs('exists','用户在线查看')){
					tabObj.tabs('select','用户在线查看');
					return;
				}else{
					title="用户在线查看";
					href='online_user_manager.jsp';
				}
			}else if(id=="rank_user_manager"){//用户在线管理
				if(tabObj.tabs('exists','用户等级查看')){
					tabObj.tabs('select','用户等级查看');
					return;
				}else{
					title="用户等级查看";
					href='rank_user_manager.jsp';
				}
			}
			tabObj.tabs('add',{    
				title:title,    
				href:href,
				fit:true,
				closable:true,    
			});
		}
	});
	$('#menu_tree2_admin').tree({
		onClick: function(node){
			var id=node.id;//获取点击的节点id
			var title="小萌订餐";
			var href="yc.html";
			var tabObj=$('#admin_content_info');
			if(id=="add_menu"){//添加菜谱
				if(tabObj.tabs('exists','添加菜谱')){
					tabObj.tabs('select','添加菜谱');
					return;
				}else{
					title="添加菜谱";
					href='add_food.jsp';
				}
			}else if(id=="manager_menu"){
				if(tabObj.tabs('exists','管理菜谱')){
					tabObj.tabs('select','管理菜谱');
					return;
				}else{
					title="管理菜谱";
					href='manager_food.jsp';
				}
			}
			tabObj.tabs('add',{    
				title:title,    
				href:href,
				fit:true,
				closable:true,    
			});
		}
	});
	$('#menu_tree3_admin').tree({
		onClick: function(node){
			var id=node.id;//获取点击的节点id
			var title="小萌订餐";
			var href="yc.html";
			var tabObj=$('#admin_content_info');
			if(id=="manager_order"){//订单管理
				if(tabObj.tabs('exists','订单管理')){
					tabObj.tabs('select','订单管理');
					return;
				}else{
					title="订单管理";
					href='manager_order.jsp';
				}
			}else if(id=="order_Report"){//订单报表
				if(tabObj.tabs('exists','订单报表')){
					tabObj.tabs('select','订单报表');
					return;
				}else{
					title="订单报表";
					href='orderReport.jsp';
				}
			}
			tabObj.tabs('add',{    
				title:title,    
				href:href,
				fit:true,
				closable:true,    
			});
		}
	});




})