package com.zd.biz;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CollectBiz {
	public int removeCollect(String name,int fid);
	public Object[] getCollect(String userid);
	public void setCollect(double score, String collect, String userid, String page) throws IOException;
}
