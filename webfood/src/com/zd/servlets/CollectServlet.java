package com.zd.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zd.bean.Resfood;
import com.zd.biz.CollectBiz;
import com.zd.biz.ResfoodBiz;
import com.zd.biz.impl.CollectBizImpl;
import com.zd.biz.impl.ResfoodBizImpl;
import com.zd.web.model.JsonModel;

public class CollectServlet extends BasicServlet{
	private static final long serialVersionUID = 1L;
	private static final ResfoodBiz resfood=new ResfoodBizImpl();
	private static final CollectBiz cb=new CollectBizImpl();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("getCollect".equals(op)){
			getCollect(request,response);
		}else if("setCollect".equals(op)){
			setCollect(request,response);
		}else if("removeCollect".equals(op)){
			removeCollect(request,response);
		}
	}
	private void removeCollect(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid=Integer.parseInt(request.getParameter("userid"));
		String content=request.getParameter("content");
		int result=cb.removeCollect(content, userid);
		JsonModel jm=new JsonModel();
		if(result==1){
			jm.setCode(1);
			super.outJson(jm, response);
		}else{
			jm.setCode(0);
			super.outJson(jm, response);
		}
		
		
		
	}
	private void setCollect(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid=request.getParameter("userid");
			String page=request.getParameter("page");
			String data=request.getParameter("data");
			JsonModel jm=new JsonModel();
			double score = 0;
			score=new Date().getTime();
			cb.setCollect(score,data,userid,page);
			Object[] obj=cb.getCollect(userid);
			jm.setCode(1);
			jm.setObj(obj);
			super.outJson(jm, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	private void getCollect(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userid=request.getParameter("userid");
		Object[] obj=cb.getCollect(userid);
		JsonModel jm=new JsonModel();
		if(obj!=null){
			jm.setCode(1);
			jm.setObj(obj);
		}else{
			jm.setCode(0);
			jm.setErrorMsg(" emtpy collect");
		}
		super.outJson(jm, response);
	}
	
	
}
