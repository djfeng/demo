package com.bocweb.core.util.sql;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
/**
 * SQL中in查询条件的参数转换
 * @author tongpuxin
 */
public class SqlInConvert {

	/**
	 * 将list集合转换成sql语句的in形式
	 * String: '1','2','3'
	 */
	public static String listString2String(List<String> lists){
		String ids = "";
		if(lists!=null && lists.size()>0){
			for (int i = 0; i < lists.size(); i++) {
				String id = lists.get(i);
				ids += "'"+id+"',";
			}
			if(StringUtils.isNotBlank(ids))ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	/**
	 * 将list集合转换成sql语句的in形式
	 * Long: 1,2,3
	 */
	public static String listLong2String(List<Long> lists){
		String ids = "";
		if(lists!=null && lists.size()>0){
			for (int i = 0; i < lists.size(); i++) {
				Long id = lists.get(i);
				ids += ""+id+",";
			}
			if(StringUtils.isNotBlank(ids))ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	/**
	 * 将set集合转换成sql语句的in形式
	 * String: '1','2','3'
	 */
	public static String setString2String(Set<String> lists){
		String ids = "";
		if(lists!=null && lists.size()>0){
			for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
				String id = (String) iterator.next();
				ids += "'"+id+"',";
			}
			if(StringUtils.isNotBlank(ids))ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	/**
	 * 将set集合转换成sql语句的in形式
	 * Long: 1,2,3
	 */
	public static String setLong2String(Set<Long> lists){
		String ids = "";
		if(lists!=null && lists.size()>0){
			for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
				Long id = (Long) iterator.next();
				ids += ""+id+",";
			}
			if(StringUtils.isNotBlank(ids))ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	
	/**
	 * 将array数组转换成sql语句的in形式
	 * String: '1','2','3'
	 */
	public static String arrayString2String(String allids[]){
		String ids = "";
		if(allids!=null && allids.length>0){
			for (int i = 0; i < allids.length; i++) {
				String id = allids[i];
				ids += "'"+id+"',";
			}
			if(StringUtils.isNotBlank(ids))ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	/**
	 * 将array数组转换成sql语句的in形式
	 * Long: 1,2,3
	 */
	public static String arrayLong2String(Long allids[]){
		String ids = "";
		if(allids!=null && allids.length>0){
			for (int i = 0; i < allids.length; i++) {
				Long id = allids[i];
				ids += ""+id+",";
			}
			if(StringUtils.isNotBlank(ids))ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	/**
	 * 将array数组转换成sql语句的in形式
	 * Serializable: 1,2,3
	 */
	public static String array2String(Serializable allids[]){
		String ids = "";
		if(allids!=null && allids.length>0){
			for (int i = 0; i < allids.length; i++) {
				Serializable id = allids[i];
				if(!ids.equals(""))ids += ",";
				if(id instanceof Integer || id instanceof Long){
					ids += id;
				}else if(id instanceof String){
					ids += "'"+id+"'";
				}else{
					ids += "'"+id+"'";
				}
			}
		}
		return ids;
	}
}
