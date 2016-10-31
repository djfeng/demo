package com.bocweb.core.vo;

import com.bocweb.core.util.ConditionUtil;

/**
 * 自定义查询-查询条件及属性值
 * 
 * @author tongpuxin
 */
public class Condition {

	/**
	 * 查询条件
	 */
	private String 	key;
	
	/**
	 * 属性值
	 */
	private Object	value;
	
	/**
	 * 构造函数
	 */
	public Condition(){
		
	}
	
	/**
	 * 构造函数
	 * @param key	查询条件
	 * @param value	属性值
	 */
	public Condition(String key, Object value){
		this.setKey(key);
		this.setValue(value);
	}
	
	/**
	 * 获取查询条件
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * 设置查询条件
	 * @param key	查询条件
	 */
	public void setKey(String key) {
		this.key = ConditionUtil.getParamName(key);
	}
	
	/**
	 * 获取属性值
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * 设置属性值
	 * @param value	属性值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
