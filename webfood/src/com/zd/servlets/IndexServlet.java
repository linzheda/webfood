package com.zd.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zd.bean.Resfood;
import com.zd.bean.Resuser;
import com.zd.biz.ResfoodBiz;
import com.zd.biz.impl.ResfoodBizImpl;
import com.zd.utils.ZdConstant;
import com.zd.web.model.JsonModel;
import com.zd.web.model.JsonModelIndex;

/**
 * 主页面加载将多个请求合并
 * @author linzd
 *
 */
public class IndexServlet extends BasicServlet{
	private static final long serialVersionUID = 1L;
	private ResfoodBiz resfoodBiz=new ResfoodBizImpl();
	JsonModelIndex jmi=new JsonModelIndex();
	Map<String,JsonModel> map=new HashMap<String,JsonModel>();
	private int pagesize=5;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			List<Resfood> list;
			try {
				list = resfoodBiz.findAll();
				int page=list.size()/pagesize;
				if(page%pagesize==0){
					
				}else{
					page++;
				}
				
				if("index".equals(op)){
					try {
						indexOp(request,response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("syy".equals(op)){
					int dqy=Integer.parseInt(request.getParameter("dqy"));
					dqy=dqy-1;
					if(dqy<=1){
						dqy=1;
					}
					if(dqy>=page){
						dqy=page;
					}
					fyOp(request,response,dqy);
				}else if("xyy".equals(op)){
					int dqy=Integer.parseInt(request.getParameter("dqy"));
					dqy=dqy+1;
					if(dqy<=1){
						dqy=1;
					}
					if(dqy>=page){
						dqy=page;
					}
					fyOp(request,response,dqy);
				}else if("sy".equals(op)){
					int dqy=Integer.parseInt(request.getParameter("dqy"));
					dqy=1;
					fyOp(request,response,dqy);
				}else if("wy".equals(op)){
					int dqy=Integer.parseInt(request.getParameter("dqy"));
					dqy=page;
					fyOp(request,response,dqy);
				}else if("tz".equals(op)){
					int dqy=Integer.parseInt(request.getParameter("dqy"));
					fyOp(request,response,dqy);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			 
		
		
		
	}
	
	private void fyOp(HttpServletRequest request, HttpServletResponse response ,int dqy) throws IOException {
		JsonModel jm=new JsonModel();
		try {
			List<Resfood> list=resfoodBiz.findAll();
			List<Resfood> list2=doPagination(pagesize,dqy,list);
			
			jm.setCode(dqy);
			jm.setObj(list2);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.getMessage());
		}
		super.outJson(jm, response);
	}
	//页面加载时
	private void indexOp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		findAll(request,response);
		findcartOp(request,response);
		isloginOp(request,response);
		super.outJson(jmi, response);
		
	}
	
	//----------------------------------
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonModel jm=new JsonModel();
		try {
			List<Resfood> list=resfoodBiz.findAll();
			list=doPagination(pagesize,1,list);
			jm.setCode(1);
			jm.setObj(list);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.getMessage());
		}
		map.put("findAll", jm);
		jmi.setJmMap(map);
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
		
		map.put("findcartOp", jm);
		jmi.setJmMap(map);
	}
	private void isloginOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		JsonModel jm=new JsonModel();
		if(session.getAttribute(ZdConstant.LOGIN_USER)!=null){
			Resuser resuser=(Resuser)session.getAttribute(ZdConstant.LOGIN_USER);
			jm.setCode(1);
			resuser.setPwd(null);
			jm.setObj(resuser);
			
		}else{
			jm.setCode(0);
			
		}
		map.put("isloginOp", jm);
		jmi.setJmMap(map);
	}
	///----list分页
	public List<Resfood> doPagination(int pagesize,int index,List<Resfood> list){
		List<Resfood> list2=new ArrayList<Resfood>();
		try{
			int i=pagesize*(index-1);
			while(i<pagesize*index){
				if ( i<=(list.size()-1) ){
					Resfood resfood=list.get(i);               
					list2.add(resfood);
				}else{
					break;
				}
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list2;
	}
	

}
