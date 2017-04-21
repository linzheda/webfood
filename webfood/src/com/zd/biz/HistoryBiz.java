package com.zd.biz;

import java.io.IOException;
import java.util.Set;

/**
 * 历史记录的业务层
 * @author linzd
 *
 */
public interface HistoryBiz {
	/**
	 * 计算历史记录的算法
	 * @param score
	 * @param historyData
	 * @throws IOException 
	 */
	public void setHistory(double score,String historyData,String userid,String page) throws IOException;
	public Object[] getHistory(String userid );
	
	
}
