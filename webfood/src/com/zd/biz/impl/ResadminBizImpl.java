package com.zd.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.zd.bean.Resadmin;
import com.zd.biz.ResadminBiz;
import com.zd.dao.DBHelper;
import com.zd.utils.Encrypt;


public class ResadminBizImpl implements ResadminBiz {
	private DBHelper db=new DBHelper();
	@Override
	public Resadmin login(Resadmin resadmin) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		resadmin.setRapwd(Encrypt.md5(resadmin.getRapwd()));
		String sql="select * from resadmin where raname=? and rapwd=? ";
		
		List<Object> params=new ArrayList<Object>();
		params.add(resadmin.getRaname());
		params.add(resadmin.getRapwd());
		List<Resadmin> list=db.find(sql,Resadmin.class, params);
		resadmin=list.size()>0?list.get(0):null;
		return resadmin;
	}

}
