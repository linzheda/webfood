package com.zd.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zd.bean.Resfood;
import com.zd.bean.Resorder;
import com.zd.bean.Resorderitem;
import com.zd.bean.Resuser;
import com.zd.biz.ResfoodBiz;
import com.zd.biz.ResorderBiz;
import com.zd.biz.impl.ResfoodBizImpl;
import com.zd.biz.impl.ResorderBizImpl;
import com.zd.biz.impl.UserRankBizImpl;
import com.zd.utils.ZdConstant;
import com.zd.web.model.JsonModel;

@SuppressWarnings("rawtypes")
public class ResorderServlet extends BasicServlet{

	private static final long serialVersionUID = 1L;
	private ResorderBiz rb=new ResorderBizImpl();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("order".equals(op)){//点击加入购物车
			orderOp(request,response);
		}else if("clearall".equals(op)){
			clearallOp(request,response);//清空购物车
		}else if("delorder".equals(op)){//删除一个商品
			delorder(request,response);
		}else if("findcart".equals(op)){//查看购物车是否有数据
			findcartOp(request,response);
		}else if("confirmorder".equals(op)){//确认收货地址
			confirmorderOp(request,response);
		}else if("findOrder".equals(op)){//查询订单
			findOrder(request,response);
		}else if("findOrderItem".equals(op)){//查询订单详细
			findOrderItem(request,response);
		}else if("makeReport".equals(op)){//生成报表
			makeReport(request,response);
		}else if("updateStatus".equals(op)){//修改状态
			updateStatus(request,response);
		}
	}
	
	private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String roid=request.getParameter("roid");
		String status=request.getParameter("status");
		if(status.equals("1")){
			status="0";
		}else if(status.equals("0")){
			status="1";
		}
		int result=rb.updateStutus(roid,status);
		JsonModel jm=new JsonModel();
		if(result==1){
			jm.setCode(1);
		}else{
			jm.setCode(0);
		}
		super.outJson(jm, response);
		
		
	}

	private void makeReport(HttpServletRequest request, HttpServletResponse response) {
		String dateStart=request.getParameter("dateStart");
		String dateEnd=request.getParameter("dateEnd");
		List<Map<String, Object>> list=rb.makeReport(dateStart, dateEnd);
		this.out(response, list);
	}

	private void findOrderItem(HttpServletRequest request, HttpServletResponse response) {
		int roid=Integer.parseInt(request.getParameter("roid"));
		try {
			List<Resorderitem> list=rb.findOrderItem(roid);
			this.out(response, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	private void findOrder(HttpServletRequest request, HttpServletResponse response) {
		Integer page=Integer.parseInt(request.getParameter("page"));
		Integer rows=Integer.parseInt(request.getParameter("rows"));
		try {
			List<Resorder> list=rb.findOrder(page-1,rows);
			
			Map<String , Object> map=new HashMap<String,Object>();
			map.put("total",rb.getTotal());
			map.put("rows",list);
			this.outJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private void confirmorderOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		JsonModel jm=new JsonModel();
		if(session.getAttribute(ZdConstant.LOGIN_USER)==null){
			jm.setCode(0);
			jm.setErrorMsg("you havenot logined in ....");
			super.outJson(jm, response);
			return;
		}
		
		Map<Integer,Resfood> cart =null;
		if(session.getAttribute(ZdConstant.CART_NAME)==null){
			jm.setCode(0);
			jm.setErrorMsg("you havenot ordered any goods....");
			super.outJson(jm, response);
			return;
		}
		cart=(Map<Integer, Resfood>) session.getAttribute(ZdConstant.CART_NAME);
		
		Resuser resuser=(Resuser) session.getAttribute(ZdConstant.LOGIN_USER);
		Resorder resorder=(Resorder) super.parseRequest(request, Resorder.class);
		resorder.setStatus(0);
		resorder.setUserid(resuser.getUserid());
		
		try {
			rb.submitOrder(resorder, cart);
			
			double total=0;
			for(Map.Entry<Integer, Resfood> entry:cart.entrySet() ){
				total+=entry.getValue().getRealprice()* entry.getValue().getNum();
			}
			UserRankBizImpl urbi=new UserRankBizImpl();
			urbi.updateScore(total, resuser.getUserid());
			
			
			session.removeAttribute(ZdConstant.CART_NAME);
			jm.setCode(1);
			super.outJson(jm, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.getMessage());
			super.outJson(jm, response);
		}
	}
	private void findcartOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		Map<Integer,Resfood> cart=null;
		if(session.getAttribute(ZdConstant.CART_NAME)!=null){
			cart=(Map<Integer, Resfood>) session.getAttribute(ZdConstant.CART_NAME);
		}
		JsonModel jm=new JsonModel();
		if(cart==null){
			jm.setCode(0);
		}else {
			jm.setCode(1);
			jm.setObj(cart);
		}
		super.outJson(jm, response);
	}

	private void delorder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resfood resfood=(Resfood) super.parseRequest(request, Resfood.class);
		HttpSession session=request.getSession();
		Map<Integer,Resfood> cart =new HashMap<Integer,Resfood>();
		if(session.getAttribute(ZdConstant.CART_NAME)!=null){
			cart=(Map<Integer, Resfood>) session.getAttribute(ZdConstant.CART_NAME);
		}
		if(cart.containsKey(resfood.getFid())){
			cart.remove(resfood.getFid());
		}
		session.setAttribute(ZdConstant.CART_NAME,cart);
		JsonModel jm=new JsonModel();
		jm.setCode(1);
		jm.setObj(cart);
		super.outJson(jm, response);
		
	}


	private void clearallOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		session.removeAttribute(ZdConstant.CART_NAME);
		JsonModel jm=new JsonModel();
		jm.setCode(1);
		super.outJson(jm, response);
		
	}


	private void orderOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resfood resfood=(Resfood) super.parseRequest(request, Resfood.class);//取出fid


		//判断Session中的map是否存在，
		//不存在说明这个用户是第一次下单，创建一个map作为购物车存到Session中
		//存在说明买过商品  从Session中取出这个map
		//处理数量  不存在数量为1，存在则数量相加
		HttpSession session=request.getSession();
		Map<Integer,Resfood> cart=new HashMap<Integer,Resfood>();
		if(session.getAttribute(ZdConstant.CART_NAME)!=null){
			cart=(Map<Integer, Resfood>) session.getAttribute(ZdConstant.CART_NAME);
		}
		JsonModel jm=new JsonModel();
		try {
			if(cart.containsKey(resfood.getFid() )){
				Resfood rfold=cart.get(resfood.getFid());
				if(resfood.getNum()+rfold.getNum()<=0){
					cart.remove(rfold.getFid());
				}else{
					rfold.setNum(resfood.getNum()+rfold.getNum());
					cart.put(resfood.getFid(), rfold);
				}
				
			
			}else{
				ResfoodBiz rb=new ResfoodBizImpl();
				Resfood rf=rb.getResfoodByFid(resfood.getFid());
				rf.setNum(resfood.getNum());
				cart.put(resfood.getFid(), rf);
			}
			session.setAttribute(ZdConstant.CART_NAME, cart);
			jm.setCode(1);
			jm.setObj(cart);
			
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.toString());
		}
		
		super.outJson(jm, response);
		
		
		
	}
}
