package com.zd.biz;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.zd.bean.Resuser;

public interface ResuserBiz {
	public Resuser login(Resuser resuser) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public int register(Resuser resuser);
	public List<Resuser> findUser(int pagesize, int page) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public int getTotal();
	public int updateInfo(String username, String email, String userid);

}
