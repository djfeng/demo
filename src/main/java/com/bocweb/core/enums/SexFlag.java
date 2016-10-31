package com.bocweb.core.enums;


/**
 * 性别标记
 * MALE: 男
 * FEMALE: 女
 * BAOMI: 保密
 */
public enum SexFlag {

	/**
	 * 男
	 */
	MALE("男"), 
	/**
	 * 女
	 */
	FEMALE("女"), 
	/**
	 * 保密
	 */
	BAOMI("保密");

	private String message;

	SexFlag(String message) {
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
