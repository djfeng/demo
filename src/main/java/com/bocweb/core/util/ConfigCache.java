package com.bocweb.core.util;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
/**
 * 资源文件缓存
 * @author tongpuxin
 */
public class ConfigCache {
	static Logger logger = Logger.getLogger(Config.class.getName());	
	
	/**
	 * 缓存配置文件,避免每次加载读取配置文件
	 * key: 资源文件名_key
	 * value: String
	 */
	private static HashMap<String, String> CACHE_VALUES = new HashMap<String, String>();
	
	/**
	 * 缓存配置项，根据资源文件名称
	 * @param fileName	资源文件名称
	 */
	public static void cacheConfig(String fileName){
		logger.info("缓存"+fileName+".properties资源文件的所有属性值[start]");
		CACHE_VALUES.put(fileName, fileName);//记录已被全部缓存的资源文件名
		ResourceBundle resource = ResourceBundle.getBundle(fileName);
		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			logger.info("缓存["+key+"]属性值");
			StringBuilder rkey = new StringBuilder();
			rkey.append(fileName).append("_").append(key);
			CACHE_VALUES.put(rkey.toString(), resource.getString(key));
        }
		logger.info("缓存"+fileName+".properties资源文件的所有属性值[end]");
	}
	
	/**
	 * 缓存配置项
	 * @param key	资源文件名称_属性名称
	 * @param value	属性值
	 */
	public static void cacheAttribute(String key, String value) {
		CACHE_VALUES.put(key, value);
	}
	
	/**
	 * 从缓存中获取配置项
	 * 根据fileName和key获取
	 * @param key	资源文件名称_属性名称
	 */
	public static String getAttribute(String key) {
		return CACHE_VALUES.get(key);//从缓存中读取
	}
	
	/**
	 * 判断资源文件是否已全部缓存
	 * @param fileName	资源文件名称
	 */
	public static boolean isAllCache(String fileName){
		String fName = CACHE_VALUES.get(fileName);
		if(StringUtils.isNotBlank(fName)){
			return true;
		}
		return false;
	}
}
