package com.bocweb.core.util;


import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 加载系统资源文件工具类
 * 可在容器启动时通过cacheConfig(String fileName)方法缓存资源文件所有配置项
 * @author tongpuxin
 */
public class Config {
	static Logger logger = Logger.getLogger(Config.class.getName());

	private ResourceBundle resource = null;
	private String fileName;
	
	public void setResource(ResourceBundle resource) {
		this.resource = resource;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	/**
	 * 缓存配置项，根据资源文件名称
	 * @param fileName	资源文件名称
	 */
	public static void cacheConfig(String fileName){
		ConfigCache.cacheConfig(fileName);
	}
	
	/**
	 * 根据key获取application资源文件的属性值
	 * 要读取其他文件先通过getInstance(String fileName)获取单例,再通过getValue(String key)获取属性值
	 * @param key	属性名称
	 */
	public static String getAttribute(String key) {
		String resourceName = "application";
		String rkey = new StringBuilder().append(resourceName).append("_").append(key).toString();
		String value = ConfigCache.getAttribute(rkey);//先从缓存中读取
		if(StringUtils.isBlank(value) && !ConfigCache.isAllCache(resourceName)){//缓存中没有，又没有全部缓存，则从文件中读取
			try {
				ResourceBundle resource = ResourceBundle.getBundle(resourceName);
				logger.info("加载"+resourceName+".properties资源文件的["+key+"]属性值");
				value = resource.getString(key);
				ConfigCache.cacheAttribute(rkey, value);
			} catch (RuntimeException e) {
				logger.warn("资源文件"+resourceName+".properties没有["+key+"]属性");
			}
		}
		return value;
	}
	
	/**
	 * 获取单例
	 * @param fileName	资源文件名称
	 */
	public static Config getInstance(String fileName){
		Config instance = new Config();
		instance.setFileName(fileName);
		if(!ConfigCache.isAllCache(fileName)){//未被全部缓存，则加载文件
			logger.info("加载"+fileName+".properties资源文件");
			instance.setResource(ResourceBundle.getBundle(fileName));
		}
		return instance;
	}
	
	/**
	 * 根据key获取属性值
	 * @param key	属性名称
	 */
	public String getValue(String key){
		String rkey = new StringBuilder().append(fileName).append("_").append(key).toString();
		String value = ConfigCache.getAttribute(rkey);//先从缓存中读取
		if(StringUtils.isBlank(value) && !ConfigCache.isAllCache(fileName)){//缓存中没有，又没有全部缓存，则从文件中读取
			logger.info("加载"+fileName+".properties资源文件的["+key+"]属性值");
			value = resource.getString(key);
			ConfigCache.cacheAttribute(rkey, value);
		}
		return value;
	}
}
