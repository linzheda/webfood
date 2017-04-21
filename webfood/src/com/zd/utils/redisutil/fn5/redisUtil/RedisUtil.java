package com.zd.utils.redisutil.fn5.redisUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zd.bean.Resfood;

import redis.clients.jedis.Jedis;

public class RedisUtil <T>{
	public void saveToHash(Jedis jedis,String keyPrefix,String id,List<T> list,Class<T> c) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		if(jedis==null||jedis.isConnected()==false){
			return;
		}
		
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
				Object	value=m.invoke(rf);
				if(value!=null){
					jedis.hset(itemid,fieldName,m.invoke(rf).toString());
				}
				
			}
		}
	}

	public List<T> getFromHash(Jedis jedis, String keyPrefix, String id, Class c) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<T> list=new ArrayList<T>();
		String keyPattern=keyPrefix;
		Set<String> keyset=jedis.keys(keyPattern);
		Iterator<String> its=keyset.iterator();
		T t=null;
		while(its.hasNext()){
			String key=its.next();
			Map<String,String> map=jedis.hgetAll(key);
			t=parseMapToT(map,c);
			list.add(t);
		}
		return  list;
	}


	private T parseMapToT(Map<String, String> map, Class<T> c) {
		Set<Method> setMethod=getSetMethod(c);
		T t=null;
		try {
			t= c.newInstance();
			for(Method method:setMethod){
				Set<String> keys=map.keySet();
				for(String key:keys){
					String methodName="set"+key.substring(0,1).toUpperCase()+key.substring(1);
					if(method.getName().equals(methodName)){
						String typeName=method.getParameterTypes()[0].getName();
						String value=map.get(key);
						if(value!=null&& !"".equals(value)){
							if("java.lang.Integer".equals(typeName)||"int".equals(typeName)){
								method.invoke(t, Integer.valueOf(value));
							}else if("java.lang.Double".equals(typeName)||"double".equals(typeName)){
								method.invoke(t,Double.valueOf(value));
							}else if("java.lang.Long".equals(typeName)||"long".equals(typeName)){
								method.invoke(t,Long.valueOf(value));
							}else if("java.lang.Float".equals(typeName)||"float".equals(typeName)){
								method.invoke(t,Float.valueOf(value));
							}else{
								method.invoke(t,value);
							}
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
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
