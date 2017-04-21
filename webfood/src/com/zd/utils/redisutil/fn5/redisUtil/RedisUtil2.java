package com.zd.utils.redisutil.fn5.redisUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zd.bean.Resfood;

import redis.clients.jedis.Jedis;

public class RedisUtil2 <T>{
	public void saveToHash(Jedis jedis,String keyPrefix,String id,List<T> list,Class<T> c) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		String getIdMethodName="get"+id.substring(0,1).toUpperCase()+id.substring(1);
		//取出所有的get方法
		Set<Method> methodGet=getMethod(c);
		for(T rf:list){
			//取出id
			String itemid = keyPrefix +":";
			for(Method m:methodGet){
				if(m.getName().equals(getIdMethodName)){
					itemid=itemid+m.invoke(rf).toString();
					break;
				}
			}
			for(Method m:methodGet){
				String fieldName =m.getName().substring(3,4).toLowerCase()+m.getName().substring(4);
				jedis.hset(itemid,fieldName,m.invoke(rf).toString());
			}
		}
	}

	public List<T> getFromHash(Jedis jedis, String keyPrefix, String id, Class c) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Set<String>keys= jedis.keys(keyPrefix+":*");
		Set<Method> methodSet=getSetMethod(c);
		List list=new ArrayList();
		
		for(String key:keys){
			Map<String,String> map=jedis.hgetAll(key);
			Object obj=c.newInstance();
			for(Map.Entry<String,String> entry: map.entrySet()){
				String k=entry.getKey();
				String value=entry.getValue();
				for(Method m:methodSet){
					String fieldName =m.getName().substring(3,4).toLowerCase()+m.getName().substring(4);
					if(fieldName.equals(k)){
						String mt=m.getGenericParameterTypes()[0].toString();
						if(mt.equals("class java.lang.String")){
							m.invoke(obj,value);
						}else if(mt.equals("class java.lang.Integer")){
							m.invoke(obj,Integer.valueOf(value));
						}else if(mt.equals("class java.lang.Double")){
							m.invoke(obj,Double.valueOf(value));
						}else if(mt.equals("class java.lang.Boolean")){
							m.invoke(obj,Boolean.valueOf(value));
						}else {
							m.invoke(obj,value);
							System.out.println(2222);
						}
						
						break;
					}
					
				}
				
			}
			list.add(obj);
		}
		return  list;
	}


	private Set<Method> getMethod(Class c) {
		Method[] ms=c.getMethods();
		Set<Method> result=new HashSet<Method>();
		for(Method m:ms){
			if(m.getName().startsWith("get")){
				result.add(m);
			}
		}
		return result;
	}

	private Set<Method> getSetMethod(Class c) {
		Method[] ms=c.getMethods();
		Set<Method> result=new HashSet<Method>();
		for(Method m:ms){
			if(m.getName().startsWith("set")){
				result.add(m);
			}
		}
		return result;
	}


}
