package com.zd.biz;

import java.lang.reflect.InvocationTargetException;

import com.zd.bean.Resadmin;


public interface ResadminBiz {
	public Resadmin login(Resadmin resuser) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
