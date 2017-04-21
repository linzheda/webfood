package com.zd.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.zd.bean.Resfood;
import com.zd.biz.ResfoodBiz;
import com.zd.biz.impl.ResfoodBizImpl;
import com.zd.utils.FileUploadUtil;
import com.zd.web.model.JsonModel;

public class ResfoodServlet extends BasicServlet{
	private static final long serialVersionUID = 1L;
	private ResfoodBiz resfoodBiz=new ResfoodBizImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("".equals(op)){
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}else if("findAll".equals(op)){
			try {
				findAll(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("findFoods".equals(op)){
				try {
					findFoods(request,response);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else if("addFood".equals(op)){//增加菜品
			addFood(request,response);
		}else if("updateFood".equals(op)){//修改菜品
			updateFood(request,response);
		}else if("upOrDown".equals(op)){//菜品的上架和下架
			upOrDown(request,response);
		}else if("searchFoods".equals(op)){//模糊查询
			searchFoods(request,response);
		}
	}
	private void searchFoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer page=Integer.parseInt(request.getParameter("page"));
		Integer rows=Integer.parseInt(request.getParameter("rows"));
		String type=request.getParameter("type");
		String content=request.getParameter("content");
		List<Resfood> list;
		try {
			list = resfoodBiz.search(type,content);
			Map<String , Object> map=new HashMap<String,Object>();
			map.put("total",list.size());
			map.put("rows",list);
			this.outJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
		
		
	}
	private void upOrDown(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonModel jm=new JsonModel();
		
		int fid=Integer.parseInt(request.getParameter("fid"));
		String detail=request.getParameter("detail");
		String detailps=request.getParameter("detailps");
		if("down".equals(detailps)){
			detail=detail+"_商品以下架";
		}else if("up".equals(detailps)){
			detail=detail.split("_")[0];
		}
		int result=resfoodBiz.upOrDownFood(fid,detail);
		if(result>0){
			jm.setCode(1);
		}else{
			jm.setCode(0);
		}
		super.outJson(jm, response);
	}
	private void updateFood(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		JsonModel jm=new JsonModel();
		String fphoto=request.getParameter("fphoto");
		String fname=request.getParameter("fname");
		String normprice=request.getParameter("normprice");
		String realprice=request.getParameter("realprice");
		String detail=request.getParameter("detail");
		String fid=request.getParameter("fid");
		int result=resfoodBiz.updateFood(fname,normprice,realprice,fphoto,detail,fid);
		if(result>0){
			jm.setCode(1);
		}else{
			jm.setCode(0);
		}
		super.outJson(jm, response);
	}
	private void addFood(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		response.setContentType("application/json");
		
		FileUploadUtil upload=new FileUploadUtil();
		
		PageContext pageContext=JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,1024,true);
		
		Map<String ,String> map=upload.fileUpload(pageContext);
		
		
		String fphoto=request.getParameter("fphoto");
		String fname=map.get("fname");
		String normprice=map.get("normprice");
		String realprice=map.get("realprice");
		String detail=map.get("detail");
		
		
		
		
		JsonModel jm=new JsonModel();
		
		
		
		int result;
		try {
			result = resfoodBiz.addFood(fname,normprice,realprice,fphoto,detail);
			if(result>0){
				jm.setCode(1);
			}else{
				jm.setCode(0);
			}
			super.outJson(jm, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	//管理员界面菜的查询
	private void findFoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer page=Integer.parseInt(request.getParameter("page"));
		Integer rows=Integer.parseInt(request.getParameter("rows"));
		List<Resfood> list=resfoodBiz.findAll();
		list=doPagination(rows,page,list);
		Map<String , Object> map=new HashMap<String,Object>();
		map.put("total",resfoodBiz.getTotal(null));
		map.put("rows",list);
		this.outJson(map, response);
		
	}
	//list的分页
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
	
	/**
	 * 查找菜
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonModel jm=new JsonModel();
		try {
			List<Resfood> list=resfoodBiz.findAll();
			list=doPagination(5,1,list);
			jm.setCode(1);
			jm.setObj(list);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.getMessage());
		}
		super.outJson(jm, response);
	}

	

}
