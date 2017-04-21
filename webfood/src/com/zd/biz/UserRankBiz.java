package com.zd.biz;

public interface UserRankBiz {
	/**
	 * 计算积分的算法
	 * @param total  
	 * @param userid
	 */
	public void updateScore(double total, int userid);
}
