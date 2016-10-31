package com.bocweb.core.entity;



/**
 * 登录用户接口 存入session的登录用户对象必须实现该接口
 * 
 * @author tongpuxin
 */
public interface LoginUser {

	/**
	 * 获取登录用户Id
	 * @return Long
	 */
	public Long 	getId();
	/**
	 * 获取登录用户姓名
	 * @return String
	 */
	public String 	getName();
	/**
	 * 获取登录名
	 * @return String
	 */
	public String 	getUsername();
	/**
	 * 获取用户类型
	 */
	public Integer getUserType();
	/**
	 * 获取系统用户Id(用在基于SysUser扩展时,获取SysUser的ID)
	 * @return Long
	 */
	public Long 	getSysUserId();
}