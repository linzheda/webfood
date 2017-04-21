package com.zd.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zd.bean.Resuser;
import com.zd.biz.ResuserBiz;
import com.zd.dao.DBHelper;
import com.zd.utils.Encrypt;
import com.zd.utils.ZdConstant;
import com.zd.utils.redisutil.fn1.userlogin.UserRedis;

import redis.clients.jedis.Jedis;

public class ResuserBizImpl implements ResuserBiz {
	private Jedis jedis=new Jedis(ZdConstant.REDIS_URL);
	private DBHelper db=new DBHelper();
	@Override//登入
	public Resuser login(Resuser resuser) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		resuser.setPwd(Encrypt.md5(resuser.getPwd()));
		String sql="select * from resuser where username=? and pwd=? ";
		
		List<Object> params=new ArrayList<Object>();
		params.add(resuser.getUsername());
		params.add(resuser.getPwd());
		List<Resuser> list=db.find(sql,Resuser.class, params);
		resuser=list.size()>0?list.get(0):null;
	
		if(resuser!=null){
			UserRedis.activeUsers(jedis, resuser.getUserid());
		}
		
		
		return resuser;
	}
	@Override//注册
	public int register(Resuser resuser) {
		resuser.setPwd(Encrypt.md5(resuser.getPwd()));
		String sql="insert into resuser(username,pwd,email) values(?,?,?);";
		
		List<Object> params=new ArrayList<Object>();
		params.add(resuser.getUsername());
		params.add(resuser.getPwd());
		params.add(resuser.getEmail());
		int result=db.update(sql, params);
		return result;
	}
	@Override//分页查询用户信息
	public List<Resuser> findUser(int pagesize,int page) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String sql="select * from resuser limit ?,?";
		page=page-1;
		List<Object> params=new ArrayList<Object>();
		params.add(page);
		params.add(pagesize);
		List<Resuser> list=db.find(sql, Resuser.class, params);
		return list;
	}
	@Override
	public int getTotal() {//查询用户总数
		
		String sql="select count(userid) from resuser ";
		List<String>list=db.find(sql);
		int result=Integer.parseInt(list.get(0));
		return  result;
	}
	@Override
	public int updateInfo(String username, String email,String userid) {
		String sql="update resuser set username=? ,email=? where userid=? ";
		List<Object> params=new ArrayList<Object>();
		params.add(username);
		params.add(email);
		params.add(Integer.parseInt(userid));
		int result=db.update(sql, params);
		return result;
	}
	

}
