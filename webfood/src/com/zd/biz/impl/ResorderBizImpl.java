package com.zd.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zd.bean.Resfood;
import com.zd.bean.Resorder;
import com.zd.bean.Resorderitem;
import com.zd.biz.ResorderBiz;
import com.zd.dao.DBHelper;

public class ResorderBizImpl implements ResorderBiz {
	
	private DBHelper db=new DBHelper();
	
	
	@Override
	public void submitOrder(Resorder order, Map<Integer, Resfood> cart) throws Exception {
		
		Connection con=db.getConnection();
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			con.setAutoCommit(false);//关闭隐式事物提交
			//1.插入resorder
			String sql1="insert into resorder(userid,address,tel,ordertime,deliverytime,ps,status) values(?,?,?,now(),date_format(?,'%Y-%c-%d %h:%i'),?,?) ";
			pstmt=con.prepareStatement(sql1);
			pstmt.setString(1, order.getUserid()+"");
			pstmt.setString(2, order.getAddress()+"");
			pstmt.setString(3, order.getTel()+"");
			pstmt.setString(4, order.getDeliveryTimes()+"");
			pstmt.setString(5, order.getPs()+"");
			pstmt.setString(6, order.getStatus()+"");
			pstmt.executeUpdate();
			//2.查resorder表中的最新的roid订单编号
			sql1="select max(roid) from resorder";
			pstmt=con.prepareStatement(sql1);
			rs=pstmt.executeQuery();
			String roid=null;
			if(rs.next()){
				roid=rs.getString(1);
			}else{
				throw new Exception(" db server error ...");
			}
			for(Map.Entry<Integer, Resfood> entry:cart.entrySet()){
				sql1="insert into resorderitem(roid,fid,dealprice,num) values(?,?,?,?)";
				pstmt=con.prepareStatement(sql1);
				pstmt.setString(1,roid);
				pstmt.setString(2,entry.getKey()+"");
				pstmt.setString(3,entry.getValue().getRealprice().toString()+"");
				pstmt.setString(4,entry.getValue().getNum().toString()+"");
				pstmt.executeUpdate();
			}
			
			//3.插入resorderitem
			
			
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
		
	}


	@Override
	public List<Resorder> findOrder(Integer page, Integer rows) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String sql="select * from resorder limit ?,?";
		List<Object> params=new ArrayList<Object>();
		params.add(page);
		params.add(rows);
		List<Resorder> list=db.find(sql, Resorder.class, params);
		return list;
	}


	@Override
	public int getTotal() {
		String sql="select count(roid) from resorder";
		List<String>list=db.find(sql);
		int result=Integer.parseInt(list.get(0));
		return  result;
	}


	@Override
	public List<Resorderitem> findOrderItem(int roid) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String sql="select oi.*,f.fname from resorderitem oi,resfood f where oi.fid=f.fid and  roid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(roid);
		List<Resorderitem> list=db.find(sql, Resorderitem.class, params);
		return list;
	}


	@Override
	public List<Map<String, Object>> makeReport(String dateStart, String dateEnd) {

		String sql="select f.fname as name,count(f.fname)*oi.num as num from resorderitem oi,resfood f,resorder o where oi.fid=f.fid and o.roid=oi.roid and o.ordertime between str_to_date(?,'%Y-%m-%d') and str_to_date(?,'%Y-%m-%d') group by f.fname,oi.num  ";
		List<Object> params=new ArrayList<Object>();
		params.add(dateStart);
		params.add(dateEnd);
		List<Map<String, Object>> list=db.finds(sql, params);
		return list;
	}


	@Override
	public int updateStutus(String roid,String status) {
		String sql="update resorder set status=? where roid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(status);
		params.add(roid);
		int result=db.update(sql, params);
		return result;
	}

}
