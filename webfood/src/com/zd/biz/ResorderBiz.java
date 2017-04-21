package com.zd.biz;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.zd.bean.Resfood;
import com.zd.bean.Resorder;
import com.zd.bean.Resorderitem;

public interface ResorderBiz {
	public void submitOrder(Resorder order ,Map<Integer,Resfood>cart) throws Exception;

	public List<Resorder> findOrder(Integer page, Integer rows) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;


	public int getTotal();


	public List<Resorderitem> findOrderItem(int roid) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	
	public List<Map<String,Object>> makeReport(String dateStart,String dateEnd);


	public int updateStutus(String roid, String status);


}
