package com.zd.utils;
/***
 * 这个类专门用于定义系统中所使用的字符常量
 * @author linzd
 *
 */
public class ZdConstant {
	//查看所有菜品
	public final static String ALLFOOD="allfood";
	//redis的连接地址
	public final static String REDIS_URL="localhost";
	//public final static String REDIS_URL="192.168.44.129";   //虚拟机的ip
	//redis的端口号
	public final static int REDIS_PORT=6379;
	//购物车在Session中的键名
	public final static String CART_NAME="cart";
	//
	public final static String VERIFYCODE="validateCode";
	//登入用户
	public final static String LOGIN_USER="login_user";
	
	//redis中用于保存在线用户的天数
	public final static int KEEPNDAYSFONLINENSER=7;
	//登入的管理员
	public final static String LOGIN_ADMIN="login_admin";
	
	
}
