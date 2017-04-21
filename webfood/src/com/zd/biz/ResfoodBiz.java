package com.zd.biz;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.zd.bean.Resfood;

public interface ResfoodBiz {
	public  List<Resfood> findAll() throws Exception;
	
	public Resfood getResfoodByFid(Integer fid) throws Exception;

	public int getTotal(Integer tid);

	public int addFood(String fname, String normprice, String realprice, String fphoto, String detail) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;


	public int updateFood(String fname, String normprice, String realprice, String fphoto, String detail, String fid);

	public int upOrDownFood(int fid, String detail);

	public List<Resfood> search(String type, String content) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

}
