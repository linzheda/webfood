package com.zd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.zd.bean.Resadmin;

import com.zd.biz.ResadminBiz;
import com.zd.biz.impl.ResadminBizImpl;
import com.zd.utils.ZdConstant;

public class ResadminServlet extends BasicServlet{
	private static final long serialVersionUID = 1L;
	private ResadminBiz rb=new ResadminBizImpl();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		op=request.getParameter("op");
		if("adminlogin".equals(op)){
			adminLoginOp(request,response);
		}
	}

	private void adminLoginOp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String raname=request.getParameter("raname");
		String rapwd=request.getParameter("rapwd");
		String valcode=request.getParameter("valcode");
		Resadmin resadmin=new Resadmin();
		resadmin.setRaname(raname);
		resadmin.setRapwd(rapwd);
		resadmin.setValcode(valcode);
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		if(resadmin.getRaname()==null|| "".equals(resadmin.getRaname())){
			out.print("<script>alert('admin is empty');window.location.href='back/login.jsp'</script>");
			return;
		
		}
		if(resadmin.getRapwd()==null || "".equals(resadmin.getRapwd())){
			
			out.print("<script>alert('password is empty');window.location.href='back/login.jsp'</script>");
			return;
			
		}
		HttpSession session=request.getSession();
		
		String validateCode=(String) session.getAttribute("validateCode");
		
		if(!validateCode.equalsIgnoreCase(resadmin.getValcode())){
			out.print("<script>alert('validateCode is not right');window.location.href='back/login.jsp'</script>");
			return;
		}
		try {
			resadmin=rb.login(resadmin);
			
			if(resadmin!=null){
				session.setAttribute(ZdConstant.LOGIN_ADMIN, resadmin);
				
				resadmin.setRapwd("");
				
				response.sendRedirect("back/admin.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
		
		
		
	}
	
}
