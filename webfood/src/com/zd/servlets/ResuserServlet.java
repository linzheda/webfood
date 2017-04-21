package com.zd.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zd.bean.Resuser;
import com.zd.biz.ResuserBiz;
import com.zd.biz.impl.ResuserBizImpl;
import com.zd.utils.MailUtil;
import com.zd.utils.ZdConstant;
import com.zd.utils.redisutil.fn2.rank.RankUtil;
import com.zd.web.model.JsonModel;

import redis.clients.jedis.Jedis;

public class ResuserServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	private ResuserBiz rb=new ResuserBizImpl();


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("".equals(op)){//
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}else if("login".equals(op)){
			loginOp(request,response);//登入
		}else if("loginout".equals(op)){
			loginoutOp(request,response);//登出
		}else if("islogin".equals(op)){//判断是否登入
			isloginOp(request,response);
		}else if("register".equals(op)){//用户注册
			registerOp(request,response);
		}else if("userShow".equals(op)){//显示用户信息
			userShow(request,response);
		}else if("updateUserInfo".equals(op)){//修改用户信息
			updateUserInfo(request,response);
		}else if("findRank".equals(op)){//查看用户排名
			findRank(request,response);
		}
	}

	private void findRank(HttpServletRequest request, HttpServletResponse response) {
		int topN=Integer.parseInt(request.getParameter("topn"));
		Jedis jedis=new Jedis(ZdConstant.REDIS_URL);
		Set<String> set=RankUtil.getTopN(jedis,topN);
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		int i=1;
		
		for(String uid:set ){
			Map<String,String> map=new HashMap<String,String>();
			map.put("uid", uid);
			list.add(map);
			i++;
		}
		this.out(response, list);
	}

	private void updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
		String username=request.getParameter("username");
		String email=request.getParameter("email");
		String userid=request.getParameter("userid");
		int result=rb.updateInfo(username,email,userid);
		this.out(response, result);
		
	}

	private void userShow(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer page=Integer.parseInt(request.getParameter("page"));
		Integer rows=Integer.parseInt(request.getParameter("rows"));
		List<Resuser> list;
		try {
			list = rb.findUser(rows, page);
			Map<String , Object> map=new HashMap<String,Object>();
			map.put("total",rb.getTotal());
			map.put("rows",list);
			this.outJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private void registerOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String uname=request.getParameter("register_name");
		String pwd=request.getParameter("register_pwd");
		String mail=request.getParameter("register_mail");
		Resuser resuser=new Resuser();
		resuser.setUsername(uname);
		resuser.setPwd(pwd);
		resuser.setEmail(mail);
		JsonModel jm=new JsonModel();
		int result= rb.register(resuser);
		if(result>0){
			jm.setCode(1);
			jm.setObj("注册成功....");
			MailUtil email=new MailUtil();
			try {
				email.sendMail(mail);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			super.outJson(jm, response);
		}else if(result<=0){
			jm.setCode(0);
			jm.setErrorMsg("注册失败...");
			super.outJson(jm, response);
		}
		
		
		
		
		
		
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
		super.outJson(jm,response);
		
		
	}

	private void loginoutOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		session.removeAttribute(ZdConstant.LOGIN_USER);
		JsonModel jm=new JsonModel();
		jm.setCode(1);
		super.outJson(jm, response);
	}

	private void loginOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resuser resuser=(Resuser) super.parseRequest(request,Resuser.class);
		JsonModel jm=new JsonModel();

		if(resuser.getUsername()==null|| "".equals(resuser.getUsername())){
			jm.setCode(0);
			jm.setErrorMsg("username should not be empty");
			super.outJson(jm, response);
			return;
		}
		if(resuser.getPwd()==null || "".equals(resuser.getPwd())){
			jm.setCode(0);
			jm.setErrorMsg("password should not be empty");
			super.outJson(jm, response);
			return;
		}
		HttpSession session=request.getSession();
		
		String validateCode=(String) session.getAttribute("validateCode");
		
		if(!validateCode.equalsIgnoreCase(resuser.getValcode())){
			jm.setCode(0);
			jm.setErrorMsg("valide code is not right");
			super.outJson(jm, response);
			return;
		}
		try {
			resuser=rb.login(resuser);
			
			if(resuser!=null){
				session.setAttribute(ZdConstant.LOGIN_USER, resuser);
				jm.setCode(1);
				resuser.setPwd("");
				jm.setObj(resuser);
				super.outJson(jm, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		jm.setCode(0);
		jm.setErrorMsg("error user");
		super.outJson(jm, response);
	}

}
