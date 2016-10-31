package com.bocweb.core.log;

/**
 * 日志操作类型
 */
public enum OperationType {

	LOGIN("登录"), LOGOUT("注销"), MLOGIN("移动端登录"), MLOGOUT("移动端注销"), ADD("添加"), UPDATE("更新"), DELETE("删除"), PUSH("同步");

	private String message;

	OperationType(String message) {
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
