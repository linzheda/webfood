package com.zd.test;

import java.util.List;
import java.util.Map;

import com.zd.dao.DBHelper;

public class Test {
	public static void main(String[] args) {
		DBHelper db=new DBHelper();
		String sql="select * from resfood";
		List<Map<String, Object>> list=db.finds(sql);
		for(Map<String, Object> map:list){
			System.out.println(map);
		}
		
	}

}
