package com.zd.biz.impl;

import com.zd.biz.UserRankBiz;
import com.zd.utils.ZdConstant;
import com.zd.utils.redisutil.fn2.rank.RankUtil;

import redis.clients.jedis.Jedis;

public class UserRankBizImpl implements UserRankBiz{
	private Jedis jedis=new Jedis(ZdConstant.REDIS_URL);

	@Override
	public void updateScore(double total, int userid) {
		double score =total/100;
		RankUtil.updateScore(jedis, score, userid+"");
	}
	
}
