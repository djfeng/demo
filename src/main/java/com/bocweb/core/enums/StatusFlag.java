package com.bocweb.core.enums;


/**
 * 状态标记
 * NORMAL: 正常
 * DISABLE: 禁用
 * DELETE: 删除
 */
public enum StatusFlag {

	/**
	 * 正常
	 */
	NORMAL("正常"), 
	/**
	 * 禁用
	 */
	DISABLE("禁用"), 
	/**
	 * 删除
	 */
	DELETE("删除");

	private String message;

	StatusFlag(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}

	@Override
	public String toString() {
		return this.name();
	}
}
