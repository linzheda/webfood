package com.zd.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zd.biz.HistoryBiz;
import com.zd.biz.impl.HistoryBizImpl;
import com.zd.web.model.JsonModel;

public class HistoryServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	private HistoryBiz historyBiz=new HistoryBizImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("setHistroy".equals(op)){
			setHistory(request,response);
		}else if("getHistory".equals(op)){
			getHistory(request,response);
		}





	}

	private void setHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String historyData=request.getParameter("data");
		String userid=request.getParameter("userid");
		String page=request.getParameter("page");
		//处理数据
		double score = 0;
		score=new Date().getTime();
		//System.out.println("score:"+score+"  historyData:"+historyData+"   userid:"+userid+"  page:"+page);
		//

		historyBiz.setHistory(score,historyData,userid,page);
		Object[] history=historyBiz.getHistory(userid);
		JsonModel jm=new JsonModel();
		jm.setCode(1);
		jm.setObj(history);
		super.outJson(jm, response);

	}


	private void getHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userid=request.getParameter("userid");
		Object[] history=historyBiz.getHistory(userid);
		JsonModel jm=new JsonModel();
		if(history!=null){
			jm.setCode(1);
			jm.setObj(history);
		}else{
			jm.setCode(0);
			jm.setErrorMsg(" emtpy history");
		}
		
		super.outJson(jm, response);
	}

}
