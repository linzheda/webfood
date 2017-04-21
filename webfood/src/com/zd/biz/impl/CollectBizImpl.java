package com.zd.biz.impl;

import java.io.IOException;

import com.zd.biz.CollectBiz;
import com.zd.utils.ZdConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class CollectBizImpl implements CollectBiz {
	private Jedis jedis=new Jedis(ZdConstant.REDIS_URL);
	
	@Override
	public Object[] getCollect(String userid) {
		return jedis.zrevrange(userid+"_collect", 0, -1).toArray();
	}

	@Override
	public void setCollect(double score, String collect,String userid,String page) throws IOException {
		double scores =score/10000;
		Pipeline pl=jedis.pipelined();
		pl.zadd(userid+"_collect",scores,page+"_"+collect);
		pl.expire(userid+"_collect", 30*24*60*60);
//		pl.sync();
//		if(res.get()>10){
//			pl.zremrangeByRank(userid+"_history", -1, -1);
//		}
		Response<Long> res=pl.zcard(userid+"_collect");
		pl.close();
	}

	@Override
	public int removeCollect(String content, int userid) {
		int result=jedis.zrem(userid+"_collect",content).intValue();
		return result;
	}

}
