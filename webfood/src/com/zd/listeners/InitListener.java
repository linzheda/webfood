package com.zd.listeners;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.zd.bean.Resfood;
import com.zd.biz.ResfoodBiz;
import com.zd.biz.impl.ResfoodBizImpl;

/**
 * Application Lifecycle Listener implementation class InitListener
 *加载菜的监听器
 */
@WebListener
public class InitListener implements ServletContextListener {

    public InitListener() {
    }

	
    public void contextInitialized(ServletContextEvent arg0)  { 
    	ResfoodBiz rb=new ResfoodBizImpl();
    	try {
			List<Resfood> list=rb.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    }
	
}
