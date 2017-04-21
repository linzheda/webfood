$(function(){
	/*
	//加载菜
	$.ajax({
		url:"resfood.action",
		data:"op=findAll",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.code==0){
				alert(  "服务器错误,"+ data.msg);
			}else{
				showAll(data.obj);
			}
		}	
	});
	//查找购物车
	$.ajax({
		url:"resorder.action",
		data:"op=findcart",
		type:"POST",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				showbag(data.obj);
			}
		}	
	});
	//保持登入状态
	$.ajax({
		url:"resuser.action",
		data:"op=islogin",
		type:"POST",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				haslogined=true;
			}else{
				haslogined=false;
			}
			checklogin(data.obj);
		}	
	});
	 */
	$.ajax({
		url:"index.action",
		data:"op=index",
		type:"POST",
		dataType:"JSON",
		success:function(data){
			//--加载菜
			if(data.jmMap.findAll.code==1){
				showAll(data.jmMap.findAll.obj);
			}else{
				alert(  "服务器错误,"+ data.jmMap.findAll.msg);
			}
			//--查看购物车
			if(data.jmMap.findcartOp.code==1){
				showbag(data.jmMap.findcartOp.obj);
			}else{

			}
			//--查看是否登录
			if(data.jmMap.isloginOp.code==1){
				haslogined=true;
			}else{
				haslogined=false;
			}
			checklogin(data.jmMap.isloginOp.obj);

		}	
	});


	//-------------------华丽的分割线-----------------------------------
	$("#food_syy").click(function(){//上一页
		$.ajax({
			url:"index.action",
			data:{"op":"syy","dqy":$("#food_dqy").val()},
			type:"POST",
			dataType:"JSON",
			success:function(data){
				$("#allfoods").html("");
				$("#food_dqy").val(data.code);
				showAll(data.obj);
			}	
		});


	});
	$("#food_xyy").click(function(){//下一页
		$.ajax({
			url:"index.action",
			data:{"op":"xyy","dqy":$("#food_dqy").val()},
			type:"POST",
			dataType:"JSON",
			success:function(data){
				$("#allfoods").html("");
				$("#food_dqy").val(data.code);
				showAll(data.obj);
			}	
		});


	});
	$("#food_sy").click(function(){//首页
		$.ajax({
			url:"index.action",
			data:{"op":"sy","dqy":$("#food_dqy").val()},
			type:"POST",
			dataType:"JSON",
			success:function(data){
				$("#allfoods").html("");
				$("#food_dqy").val(data.code);
				showAll(data.obj);
			}	
		});


	});
	$("#food_wy").click(function(){//尾页
		$.ajax({
			url:"index.action",
			data:{"op":"wy","dqy":$("#food_dqy").val()},
			type:"POST",
			dataType:"JSON",
			success:function(data){
				$("#allfoods").html("");
				$("#food_dqy").val(data.code);
				showAll(data.obj);
			}	
		});


	});

	








	//-----------------------------------------
	//页面一加载完，给清空购物车绑定事件
	$("#delall").click(function(){
		//判断购物车是否为空
		if( $("#bagcontainer").html()){
			$.ajax({
				url:"resorder.action",
				data:"op=clearall",
				type:"POST",
				dataType:"json",
				success:function(data){
					if(data.code==0){
						alert(  "服务器错误,"+ data.msg);
					}else{
						showbag(data.obj);
					}
				}	
			});
		}
	});

	// 保持登录状态
	$("#unshow").click(unshow);
	$("#btn").click(function(){

		$.ajax({
			url:"resuser.action",
			data:{"op":$("#op").val(),"username":$("#username").val(),"pwd":$("#pwd").val(),"valcode":$("#yzm").val()},
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.code==1){
					unshow();
					haslogined=true;
					alert("登入成功");
					checklogin(data.obj);

				}else{
					alert("登入失败"+data.errorMsg);
					haslogined=false;
				}
			}	
		});

	});

	//提交订单的按钮事件
	$("#submit").click(function(){
		$.ajax({
			url:"resorder.action",
			data:"op=confirmorder&address="+$("#address").val()+"&tel="+$("#tel").val()+"&deliverytime="+$("#deliverytime").val()+"&ps="+$("#ps").val(),
			dataType:"JSON",
			type:"POST",
			success:function(data){
				if(data.code==0){
					alert(  "购买失败"+ data.msg);
				}else{
					unshowinfo();
					showbag(data.obj);
					alert("购买成功");
				}
			}
		});
	});

	$("#showlogin").click( showLogin );//点击登入显示登入框

	$("#unshowinfo").click(unshowinfo);//点击X关闭登入框
	$("#yzm_img").click(function(){//点击验证码刷新
		$(this).attr("src","verifyCode.action?"+new Date().getTime());
	});
	//读取cookie
	if(haslogined==true){

		$.ajax({
			url:"history.action",
			data:"op=getHistory&userid="+encodeURI(userid)+"&page="+$("#food_dqy").val(),
			dataType:"JSON",
			type:"POST",
			success:function(data){
				if(data.code==1){
					var array=data.obj
					refreshlook2(array);
				}
			}
		});
		$.ajax({
			url:"collect.action?op=getCollect&fid="+index+"&userid="+encodeURI(userid)+"&page="+$("#food_dqy").val(),
			type:"GET",
			dataType:"JSON",
			success:function(data){
				if(data.code==1){
					var array=data.obj
					refreshcollect(array);
				}
			}
		});


	}else{
		refreshlook();
	}

	$("#showlook").click(showlookdiv);//给记录开关绑定事件
	$("#showcollect").click(showcollectdiv);//给收藏开关绑定事件
	$("#car").click(showcarbag);
});




var userid;//用户编号
var haslogined=false;//用来判断用户是否登录
var buyfoodidarr=[];//购买商品的编号

var allfoodsarr;//

function showAll(obj){
	allfoodsarr=obj;
	//var allfoods=$("#allfoods");
	for(var i=0;i<allfoodsarr.length;i++){
		var onefood=allfoodsarr[i];
		var li=document.createElement("li");

		//插入菜名
		var title=document.createElement("h3");
		title.innerHTML=onefood.fname;
		title.id="fid"+onefood.fid;
		li.appendChild(title);

		//插入菜单详情的div
		var fooddesc=document.createElement("div");
		yc.addClassName(fooddesc,"fooddesc");
		fooddesc.id="fooddesc"+onefood.fid;
		var foodimg=document.createElement("img");
		foodimg.src="image/foods/"+onefood.fphoto;

		yc.addClassName(foodimg,"foodimg");
		fooddesc.appendChild(foodimg);

		var art=document.createElement("article");
		fooddesc.appendChild(art);
		yc.addClassName(art,"foodprice");

		var detail=document.createElement("p");
		if(onefood.detail){
			detail.innerHTML="菜品描述："+onefood.detail;
		}else{
			detail.innerHTML="菜品描述：无";
		}
		art.appendChild(detail);

		var normprice=document.createElement("p");
		yc.addClassName(normprice,'normprice');
		normprice.innerHTML="原价：￥"+onefood.normprice;
		art.appendChild(normprice);

		var realprice=document.createElement("p");
		yc.addClassName(realprice,'realprice');
		realprice.innerHTML="特价：￥"+onefood.realprice;
		art.appendChild(realprice);

		var buybtn=document.createElement("a");
		buybtn.innerHTML="加入购物车";
		yc.addClassName(buybtn,"buybtn");
		art.appendChild(buybtn);
		
		var colbtn=document.createElement("a");
		colbtn.innerHTML="添加收藏";
		yc.addClassName(colbtn,"colbtn");
		art.appendChild(colbtn);

		fooddesc.style.display="none";
		li.appendChild(fooddesc);


		document.getElementById("allfoods").appendChild(li);

		//点击菜名显示详情
		(function(index,id){
			yc.addEvent(title,"click",function(){
				showdescs(id);
				if(haslogined==false){
					if(Cookies.get(allfoodsarr[index].fid_+"_"+$("#food_dqy").val())){
						Cookies.del(allfoodsarr[index].fid+"_"+$("#food_dqy").val());
						Cookies.set(allfoodsarr[index].fid+"_"+$("#food_dqy").val(),encodeURI(allfoodsarr[index].fname),60*24);
					}else{
						Cookies.set(allfoodsarr[index].fid+"_"+$("#food_dqy").val(),encodeURI(allfoodsarr[index].fname),60*24);
					}
					refreshlook();
				}else{
					$.ajax({
						url:"history.action?op=setHistroy",
						data:"data="+allfoodsarr[index].fid+"="+encodeURI(allfoodsarr[index].fname)+"&userid="+encodeURI(userid)+"&page="+$("#food_dqy").val(),
						dataType:"JSON",
						type:"POST",
						success:function(data){
							if(data.code==1){
								var array=data.obj
								refreshlook2(array);
							}
						}
					});

				}
			});
		})(i,onefood.fid);
		//加入购物车
		(function(index){
			$(buybtn).click(function(){
				var url="resorder.action?num=1&op=order&fid="+index;
				$.ajax({
					url:url,
					type:"GET",
					dataType:"JSON",
					success:function(data){
						if(data.code==1){
							alert("添加成功");
							showbag(data.obj);
						}else{
							alert("添加失败");
						}
					}
				});
			});	
		})(onefood.fid);
		
		
		//添加收藏
		(function(index,id){
			$(colbtn).click(function(){
				if(haslogined==false){
					showLogin();
					return;
				}
				$.ajax({
					url:"collect.action?op=setCollect",
					data:"data="+allfoodsarr[index].fid+"="+encodeURI(allfoodsarr[index].fname)+"&userid="+encodeURI(userid)+"&page="+$("#food_dqy").val(),
					dataType:"JSON",
					type:"POST",
					success:function(data){
						if(data.code==1){
							alert("收藏成功");
							var array=data.obj
							refreshlook2(array);
						}
					}
				});
			});	
		})(i,onefood.fid);

	}
}

function showdescs(index){//显示菜品的详情
	var allfoods=yc.$("allfoods");
	var titles=allfoods.getElementsByTagName("h3");
	var title=yc.$("fid"+index);
	var descs=allfoods.getElementsByTagName("div");
	var desc=yc.$("fooddesc"+index);
	for(var j=0;j<descs.length;j++){
		if(descs[j]==desc) continue;
		descs[j].style.display="none";

		if(index!=allfoodsarr[allfoodsarr.length-1].fid){
			yc.removeClassName("fid"+allfoodsarr[allfoodsarr.length-1].fid,"noradius");
		}
	}
	yc.toggleDisplay("fooddesc"+index,"block");
	if(index==allfoodsarr[allfoodsarr.length-1].fid){
		if(yc.hasClassName(title,"noradius")){
			yc.removeClassName(title,"noradius");
		}else{
			yc.addClassName(title,"noradius");
		}
	}

}


function showbag(cart){//显示购物车
	var count=0;
	for(var key in cart){
		if(cart.hasOwnProperty(key)){
			count++;
		}
	}
	//取出购物车信息
	var bag=$("#bagcontainer");
	//如果购物车为空
	if(count<=0){
		removebuy();
		calprice(cart);
		bag.html("<tr><td><p>购物车是空的，赶紧选购吧</p></td></tr>");
		$(".carbag").css(   {"bottom":"50px"}   );
		bag.css({"height":"40px"});
		return;
	}
	//不为空，则执行...
	calprice(cart);

	addbuy();

	bag.html("");
	buyfoodidarr=[];
	var theight=0;


	var str="";
	for(var key in cart){    // key ->  fid
		if(cart.hasOwnProperty(key)){
			var buyfood=cart[key];    // buyfood就是商品
			buyfoodidarr.push(key);
			str+="<tr>";

			str+="<td width='140px'>";
			str+=buyfood.fname;
			str+="</td>";

			str+="<td width='130px' class='editfoodnum'>";
			str+="<span>"+ buyfood.num+"</span>";
			str+="<b class='subfoodx' onclick='removefood("+ key+")'>X</b>";
			str+="<input type='button' value='+' onclick='editfood(1,"+  key+"  )'/>";
			str+="<input type='button' value='-' onclick='editfood(-1,"+ key+")' />";
			str+="</td>";

			str+="<td width='80px' style='color:#F69C30'>";
			str+="￥"+   (buyfood.num*buyfood.realprice);
			str+="</td>";

			str+="</tr>";
			theight++;
		}
	}
	bag.html( str );
	$(".carbag").css({"bottom":"50px"});
	bag.height(theight*40);
}
function showcarbag(){//点击购物车
	var flag=$("#carbag").css('display');
	if(flag=='none'){
		$("#carbag").css('display','block');
	}else{
		$("#carbag").css('display','none');
	}
	
}
//修改数量
function editfood(num,id){
	$.ajax({
		url: "resorder.action?op=order&num="+num+"&fid="+id,
		type:"GET",
		dataType:"JSON",
		success: function(  data  ){
			if(  data.code==1 ){
				showbag(   data.obj   );
			}else{
				alert("修改失败");
			}
		}
	});
}
//移除单个菜
function removefood(id){
	$.ajax({
		url:"resorder.action?op=delorder&fid="+id,
		type:"GET",
		dataType:"json",
		success:function(data){
			if(data.code==1){
				showbag(data.obj);
			}else{
				aert("修改失败");
			}
		}	
	});
}


function calprice(cart){//计算价格
	var price=0;
	for(var property in cart){
		if(cart.hasOwnProperty(property)){
			var food=cart[property];
			price+=food.realprice*food.num;
		}
	}
	if($("#pricetext")){
		$("#pricetext").html("￥"+price);
	}else{
		$("#car").html("<p id='pricetext'>￥"+price+"</p>");
	}

}
function removebuy(){//清空购物车
	$("#foodcount").html("购物车是空的");
	$("#foodcount").removeClass("gotobuy");
	$("#foodcount").off("click");
}

//修改结算
function addbuy(){
	$("#foodcount").html("去结算&gt;");
	$("#foodcount").addClass("gotobuy");
	$("#foodcount").click(tobuy);
}
//当结算，判断是否登录，没则登录，有则显示送货单
function tobuy(){
	if(haslogined){
		showinfo();
	}else{
		showLogin();
	}
}
function showLogin() {//显示登录页
	$("#yzm_img").attr("src","verifyCode.action?"+new Date().getTime());
	$("#login").show();
	$("#mubu").show();
}
function unshow() {//不显示登录页
	$("#login").hide();
	$("#mubu").hide();
}
function showinfo() {//显示结算页
	$("#myinfo").show();
	$("#mubu").show();
}
function checklogin(obj){//检查是否登录
	if(haslogined){
		haslogin(obj);
		return;
	}else{
		$("#showlogin").show();
		$("#exitspan").hide();
	}
}
function haslogin(obj){//用户登录后的显示
	$("#showlogin").hide();
	userid=obj.userid;
	$.ajax({
		url:"history.action",
		data:"op=getHistory&userid="+encodeURI(userid)+"&page="+$("#food_dqy").val(),
		dataType:"JSON",
		type:"POST",
		success:function(data){
			if(data.code==1){
				var array=data.obj
				refreshlook2(array);
			}
		}
	});
	
	$.ajax({
		url:"collect.action",
		data:"op=getCollect&userid="+encodeURI(userid)+"&page="+$("#food_dqy").val(),
		dataType:"JSON",
		type:"POST",
		success:function(data){
			if(data.code==1){
				var array=data.obj;
				refreshcollect(array)
			}
		}
	});
	
	$("#exitspan").show().html("欢迎您"+obj.username+",<a href='javascript:void()' onclick='javascript:clickexit()'>退出</a>")
}
function clickexit(obj){//退出登录

	$.ajax({
		url:"resuser.action?op=loginout",
		type:"GET",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				$("#showlogin").show();
				$("#exitspan").hide();
				haslogined=false;
				refreshlook();
			}
		}
	});



}
function unshowinfo(){//关闭送货地址栏
	$("#myinfo").hide();
	$("#mubu").hide();
}

function refreshlook(){//显示用户浏览记录

	var cookiearr=Cookies.getall();
	yc.$("ull").innerHTML="";
	var flag=0;
	if(!document.cookie) return ;
	for (var i = cookiearr.length-1; i >=0 ; i--) {
		var lii=document.createElement("li");
		yc.$('ull').appendChild(lii);
		var matcharr=cookiearr[i].split("=");
		lii.innerHTML=decodeURI(matcharr[1]);
		flag++;
		(function(index){
			yc.addEvent(lii,"click",function(){
				var page=index.split("_");

				$.ajax({
					url:"index.action",
					data:{"op":"tz","dqy":parseInt(page[1])},
					type:"POST",
					dataType:"JSON",
					success:function(data){
						$("#allfoods").html("");
						$("#food_dqy").val(data.code);
						showAll(data.obj);
						showdescs(parseInt(page[0]));
					}	
				});

			});
		})(matcharr[0]);

		if(flag>=9){
			break;
		}
	}
}

function refreshlook2(cookiearr){//显示用户浏览记录(redis)

	//var cookiearr=Cookies.getall();
	yc.$("ull").innerHTML="";
	var flag=0;
	for (var i = 0; i<cookiearr.length ; i++) {
		var lii=document.createElement("li");
		yc.$('ull').appendChild(lii);
		var matcharr=cookiearr[i].split("=");
		lii.innerHTML=decodeURI(matcharr[1]);
		flag++;
		(function(index){
			yc.addEvent(lii,"click",function(){
				console.info(index);
				var page=index.split("_");

				$.ajax({
					url:"index.action",
					data:{"op":"tz","dqy":parseInt(page[0])},
					type:"POST",
					dataType:"JSON",
					success:function(data){
						$("#allfoods").html("");
						$("#food_dqy").val(data.code);
						showAll(data.obj);
						showdescs(parseInt(page[1]));
					}	
				});
			});
		})(matcharr[0]+matcharr[1]);

		if(flag>=9){
			break;
		}
	}
}
//历史记录的开关
function showlookdiv(){
	if(yc.hasClassName("look","lookblock2")){
		yc.addClassName("look","lookblock3");
		yc.addClassName("showlook","showlookblock3");
		var removestr='yc.removeClassName("look","lookblock1");yc.removeClassName("showlook","showlookblock1");';
		removestr+='yc.removeClassName("look","lookblock2");yc.removeClassName("showlook","showlookblock2");'
			removestr+='yc.removeClassName("look","lookblock3");yc.removeClassName("showlook","showlookblock3");'
				setTimeout(removestr,300);
	}else{
		yc.addClassName("look","lookblock1");
		yc.addClassName("showlook","showlookblock1");
		setTimeout('yc.addClassName("look","lookblock2");yc.addClassName("showlook","showlookblock2");',300);
	}
}
//我的收藏的开关
function showcollectdiv(){
	if(yc.hasClassName("collect","collectblock2")){
		yc.addClassName("collect","collectblock3");
		yc.addClassName("showcollect","showcollectblock3");
		var removestr='yc.removeClassName("collect","collectblock1");yc.removeClassName("showcollect","showcollectblock1");';
		removestr+='yc.removeClassName("collect","collectblock2");yc.removeClassName("showcollect","showcollectblock2");'
			removestr+='yc.removeClassName("collect","collectblock3");yc.removeClassName("showcollect","showcollectblock3");'
				setTimeout(removestr,300);
	}else{
		yc.addClassName("collect","collectblock1");
		yc.addClassName("showcollect","showcollectblock1");
		setTimeout('yc.addClassName("collect","collectblock2");yc.addClassName("showcollect","showcollectblock2");',300);
	}
}
//刷新
function refreshcollect(cookiearr){
	yc.$("collectull").innerHTML="";
	var flag=0;
	for (var i = 0; i<cookiearr.length ; i++) {
		var lii=document.createElement("li");
		yc.$('collectull').appendChild(lii);
		var aaa=cookiearr[i];
		var matcharr=cookiearr[i].split("=");
		var liii=document.createElement("label");
		liii.innerHTML=decodeURI(matcharr[1]);
		
		var liia=document.createElement("a");
		liia.innerHTML="删除";
		liia.style.margin=" 0 0 0 100px";
		lii.appendChild(liii);
		lii.appendChild(liia);
		flag++;
		(function(index){
			yc.addEvent(liii,"click",function(){
				var page=index.split("_");
				$.ajax({
					url:"index.action",
					data:{"op":"tz","dqy":parseInt(page[0])},
					type:"POST",
					dataType:"JSON",
					success:function(data){
						$("#allfoods").html("");
						$("#food_dqy").val(data.code);
						showAll(data.obj);
						showdescs(parseInt(page[1]));
					}	
				});
			});
		})(matcharr[0]+matcharr[1]);
		
		
		(function(index){
			yc.addEvent(liia,"click",function(){
				$.ajax({
					url:"collect.action",
					data:{"op":"removeCollect","content":index,"userid":encodeURI(userid)},
					type:"POST",
					dataType:"JSON",
					success:function(data){
						if(data.code==1){
							alert("删除成功");
							$.ajax({
								url:"collect.action?op=getCollect&fid="+index+"&userid="+encodeURI(userid)+"&page="+$("#food_dqy").val(),
								type:"GET",
								dataType:"JSON",
								success:function(data){
									if(data.code==1){
										var array=data.obj
										refreshcollect(array);
									}
								}
							});
						}else{
							alert("删除失败");
						}
					}	
				});
			});
		})(matcharr[0]+"="+matcharr[1]);

		if(flag>=9){
			break;
		}
	}
}











