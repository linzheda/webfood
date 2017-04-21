package com.zd.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.zd.bean.Resfood;
import com.zd.biz.ResfoodBiz;
import com.zd.dao.DBHelper;
import com.zd.utils.ZdConstant;
import com.zd.utils.redisutil.fn5.redisUtil.RedisUtil;

import redis.clients.jedis.Jedis;

public class ResfoodBizImpl implements ResfoodBiz{
	private DBHelper db=new DBHelper();
	private Jedis jedis=new Jedis(ZdConstant.REDIS_URL,ZdConstant.REDIS_PORT);
	RedisUtil<Resfood> ru=new RedisUtil<Resfood>();

	@Override
	public List<Resfood> findAll() throws Exception {
		List<Resfood> list=null;
		try {
			jedis.connect();
			if(jedis.keys( ZdConstant.ALLFOOD+":*").size()>0 && jedis.isConnected()==true){
				list=ru.getFromHash(jedis,ZdConstant.ALLFOOD+":*","fid",Resfood.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list==null){
			list=db.find("select * from resfood", Resfood.class);
			RedisUtil<Resfood> ru=new RedisUtil<Resfood>();
			ru.saveToHash(jedis,ZdConstant.ALLFOOD+":*","fid",list,Resfood.class);
			//list=db.find("select * from resfood limit 1 , 5", Resfood.class);
		}
		return list;
	}
	
	@Override
	public Resfood getResfoodByFid(Integer fid) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Resfood resfood=null;
		List<Resfood> list=null;
		try {
			jedis.connect();
			if(jedis.isConnected()==true&&jedis.keys(ZdConstant.ALLFOOD+":"+fid).size()>0){
				list=ru.getFromHash(jedis, ZdConstant.ALLFOOD+":"+fid, "fid",Resfood.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(list==null){
			List<Object> params=new ArrayList<Object>();
			params.add(fid);
			list=db.find("select * from resfood where fid=?",Resfood.class, params);
		}
			
		
		if(list!=null && list.size()>0){
			resfood=list.get(0);
		}
		return resfood;
	}
	@Override
	public int getTotal(Integer tid) {
		String sql=null;
		List<String> list;
		if(tid!=null){
			sql="select count(fid) from resfood where fid=?";
			list=db.find(sql);
			
		}else{
			sql="select count(fid) from resfood";
			list=db.find(sql);
		}
		
		if(list!=null && list.size()>0){
			return Integer.parseInt(list.get(0));
		}else{
			return 0;
		}
	}
	
	@Override
	public int addFood(String fname, String normprice, String realprice, String fphoto, String detail) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String sql="insert into resfood(fname,normprice,realprice,detail,fphoto) values(?,?,?,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(fname);
		params.add(normprice);
		params.add(realprice);
		params.add(detail);
		params.add(fphoto);
		int result=db.update(sql, params);
		if(result>0){
			List<Resfood> list=db.find("select * from resfood where fname='"+fname+"' and detail='"+detail+"'  ", Resfood.class);
			ru.saveToHash(jedis,ZdConstant.ALLFOOD+":*","fid",list,Resfood.class);
		}
		return result;
	}
	@Override
	public int updateFood(String fname, String normprice, String realprice, String fphoto, String detail,String fid) {
		String sql="update resfood set fname=?,normprice=?,realprice=?,detail=?,fphoto=? where fid=? ";
		List<Object> params=new ArrayList<Object>();
		params.add(fname);
		params.add(normprice);
		params.add(realprice);
		params.add(detail);
		params.add(fphoto);
		params.add(Integer.parseInt(fid));
		int result=db.update(sql, params);
		return result;
	}
	@Override
	public int upOrDownFood(int fid,String detail) {
		String sql="update resfood set detail=? where fid=? ";
		List<Object> params=new ArrayList<Object>();
		params.add(detail);
		params.add(fid);
		int result=db.update(sql, params);
		if(result>0){
			jedis.hset("allfood:*:"+fid,"detail",detail);
		}
		return result;
	}
	@Override
	public List<Resfood> search(String type, String content) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String sql="";
		if(type.equals("fname")){
			sql="select * from resfood where fname like '%"+content+"%' ";
		}else if(type.equals("detail")){
			sql="select * from resfood where detail like '%"+content+"%' ";
		}else if(type.equals("fid")){
			sql="select * from resfood where fid = "+content+" ";
		}else if(type.equals("normprice")){
			sql="select * from resfood where normprice = "+content+" ";
		}else if(type.equals("realprice")){
			sql="select * from resfood where realprice = "+content+" ";
		}
		List<Resfood> list=db.find(sql, Resfood.class);
		
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		ResfoodBizImpl rbi=new ResfoodBizImpl();
		List<Resfood> list=rbi.findAll();
		for(Resfood f:list){
			System.out.println(f);
		}
		//System.out.println(rbi.getResfoodByFid(1));
	}

	

	

	

	

	

}
