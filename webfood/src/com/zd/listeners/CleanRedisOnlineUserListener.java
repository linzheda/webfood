package com.zd.listeners;



import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.zd.utils.ZdConstant;
import com.zd.utils.redisutil.fn1.userlogin.UserRedis;

import redis.clients.jedis.Jedis;



/**
 * Application Lifecycle Listener implementation class InitListener
 *删除
 */
@WebListener
public class CleanRedisOnlineUserListener implements ServletContextListener {
	 private Jedis jedis=new Jedis(ZdConstant.REDIS_URL);
	 private Timer timer ;

    public CleanRedisOnlineUserListener() {
    }

	
    public void contextInitialized(ServletContextEvent arg0)  { //启动定时器
    	int hour=2;
    	ServletContext application=arg0.getServletContext();
    	if(application.getAttribute("hour")!=null){
    		hour=Integer.parseInt(application.getAttribute("hour").toString());
    	}
    	
    	
    	
    	
    	Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, hour); // 控制时  
        calendar.set(Calendar.MINUTE, 0);       // 控制分  
        calendar.set(Calendar.SECOND, 0);       // 控制秒  
    	
        timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {  
            	UserRedis.keepNDaysRecord(jedis, ZdConstant.KEEPNDAYSFONLINENSER);
            }  
        }, calendar.getTime(), 1000*60*60*24);  
    	
    }

    public void contextDestroyed(ServletContextEvent arg0)  { //销毁定时器
    	
    	if(timer!=null){
    		timer.cancel();
    	}
    	
    	
    	
    }
	
}
