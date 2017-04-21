package com.zd.biz.impl;

import java.io.IOException;
import java.util.Set;

import com.zd.biz.HistoryBiz;
import com.zd.utils.ZdConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class HistoryBizImpl implements HistoryBiz {
	private Jedis jedis=new Jedis(ZdConstant.REDIS_URL);

	@Override
	public void setHistory(double score, String historyData,String userid,String page) throws IOException {
		double scores =score/10000;
		Pipeline pl=jedis.pipelined();
		pl.zadd(userid+"_history",scores,page+"_"+historyData);
		pl.expire(userid+"_history", 30*24*60*60);
		Response<Long> res=pl.zcard(userid+"_history");
//		pl.sync();
//		if(res.get()>10){
//			pl.zremrangeByRank(userid+"_history", -1, -1);
//		}
		pl.close();
	}

	@Override
	public Object[] getHistory(String userid) {
		
		return jedis.zrevrange(userid+"_history", 0, 9).toArray();
		
	}
	
	
}
