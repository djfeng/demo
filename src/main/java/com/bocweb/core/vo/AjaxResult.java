package com.bocweb.core.vo;

import com.bocweb.core.util.encrypt.AES256Util;
import com.bocweb.core.util.json.JsonUtil;

/**
 * 	请求返回结果
 * @author tongpuxin
 */
public class AjaxResult {
	/**执行成功*/
	public static final int SUCCESS = 200;
	/**执行失败*/
	public static final int FAILURE = 0;

	/**
	 * 执行结果
	 * 200: 成功
	 * 0: 失败
	 * 其它为自定义的错误代码
	 */
	private int 	returnCode;
	/**
	 * 返回对象数据是否被加密
	 */
	private boolean	secure = false;
	/**
	 * 提示信息
	 */
	private String 	msg;
	/**
	 * 返回对象
	 */
	private Object 	data;
	
	/**
	 * 构造函数
	 */
	public AjaxResult() {
	}
	/**
	 * 构造函数
	 * @param success	执行结果
	 */
	public AjaxResult(boolean success) {
		this.setResult(success);
	}
	/**
	 * 构造函数
	 * @param success	执行结果
	 * @param msg	提示信息
	 */
	public AjaxResult(boolean success, String msg) {
		this.setResult(success);
		this.msg = msg;
	}
	/**
	 * 构造函数
	 * @param success	执行结果
	 * @param data	返回对象
	 */
	public AjaxResult(boolean success, Object data) {
		this.setResult(success);
		this.data = data;
	}
	/**
	 * 构造函数
	 * @param success	执行结果
	 * @param msg	提示信息
	 * @param data	返回对象
	 */
	public AjaxResult(boolean success, String msg, Object data) {
		this.setResult(success);
		this.msg = msg;
		this.data = data;
	}
	/**
	 * 构造函数
	 * @param success	执行结果
	 * @param secure	是否加密
	 * @param data	返回对象
	 */
	public AjaxResult(boolean success, boolean secure, Object data) {
		this.setResult(success);
		this.secure = secure;
		this.data = data;
	}
	
	/**
	 * 设置执行结果
	 * @param success	执行结果
	 */
	public void setResult(boolean success) {
		if(success)
			this.returnCode = SUCCESS;
		else
			this.returnCode = FAILURE;
	}
	
	/**
	 * 获取执行结果
	 */
	public int getReturnCode() {
		return returnCode;
	}
	/**
	 * 设置执行结果
	 * @param returnCode	执行结果代码
	 */
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	/**
	 * 获取提示信息
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 设置提示信息
	 * @param msg	提示信息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 获取返回对象
	 */
	public Object getData() {
		//TODO 对AjaxResult返回的data数据进行AES256加密
		if(secure && data != null){
			if(data instanceof String){
				String jsonData = AES256Util.encrypt(data.toString());
				return jsonData;
			}else{
				String jsonData = JsonUtil.formatToStr(data);
				jsonData = AES256Util.encrypt(jsonData);
				return jsonData;
			}
		}
		return data;
	}
	/**
	 * 设置返回对象
	 * @param data	返回对象
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 获取返回数据是否被加密
	 */
	public boolean getSecure() {
		return secure;
	}
	
	/**
	 * 设置返回数据是否被加密
	 * @param secure	是否被加密
	 */
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
	/**
	 * 实例化执行失败的AjaxResult对象
	 * @param msg		提示信息
	 */
	public static AjaxResult warpError(String msg){
		return new AjaxResult(false, msg);
	}
	
	/**
	 * 实例化AjaxResult对象
	 * @param success	执行结果
	 */
	public static AjaxResult warpAjaxResult(boolean success){
		return new AjaxResult(success);
	}
	
	/**
	 * 实例化AjaxResult对象
	 * @param success	执行结果
	 * @param data	返回对象
	 */
	public static AjaxResult warpAjaxResult(boolean success, Object data){
		return new AjaxResult(success, data);
	}
	
	/**
	 * 实例化AjaxResult对象
	 * @param success	执行结果
	 * @param secure	是否加密
	 * @param data	返回对象
	 */
	public static AjaxResult warpAjaxResult(boolean success, boolean secure, Object data){
		return new AjaxResult(success, secure, data);
	}
}
