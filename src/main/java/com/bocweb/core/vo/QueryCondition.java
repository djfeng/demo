package com.bocweb.core.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.sql.JoinType;

import com.bocweb.core.entity.LoginUser;
import com.bocweb.core.util.ConditionUtil;

/**
 * 动态组装查询条件
 * 支持分页、排序
 * @author tongpuxin
 */
public class QueryCondition extends Page {
	private static final long serialVersionUID = 6862180823167819275L;

	/**
	 * 过滤重复
	 */
	private Boolean 	resultTransformer = false;
	/**
	 * 当前登录用户
	 */
	private LoginUser 	loginUser;
	
	public QueryCondition(){}
	

	public QueryCondition(Boolean isPage){
		super(isPage);
	}
	/**
	 * 查询条件集合
	 * key: 
	 * value: 
	 */
	private Map<String, Object> queryParams = null;
	/**
	 * OR查询条件集合
	 * key: 
	 * value: 
	 */
	private List<Condition[]> queryORParams = null;
	/**
	 * 级联加载的对象
	 * <级联对象, 加载类型(JoinType)>
	 */
	private Map<String, JoinType> FetchMode = new HashMap<String, JoinType>();
	
	/**
	 * 添加查询条件
	 * @param key	key值要遵循指定的规范,否则无效
	 * @param value	属性值
	 */
	public QueryCondition put(String key, Object value){
		key = ConditionUtil.getParamName(key);
		this.getQueryParams().put(key, value);
		return this;
	}
	/**
	 * 添加两个属性的OR查询条件
	 * @param key1		属性1的查询条件，要遵循自定义查询的规范
	 * @param value1	属性1的值
	 * @param key2		属性2的查询条件，要遵循自定义查询的规范
	 * @param value2	属性2的值
	 */
	public QueryCondition or(String key1, Object value1, String key2, Object value2){
		this.or(new Condition(key1, value1), new Condition(key2, value2));
		return this;
	}
	/**
	 * 添加一组or查询:key是查询条件,要遵循指定的规范,否则无效;value是属性值
	 * @param orCondition
	 * @return
	 */
	public QueryCondition or(Condition... orCondition){
		this.getQueryORParams().add(orCondition);
		return this;
	}
    /**
     * 设置级联加载的对象
     * @param fetch	需加载的对象
     */
    public QueryCondition setFetchMode(String fetch){
    	FetchMode.put(fetch, JoinType.LEFT_OUTER_JOIN);
    	return this;
    }
	/**
     * 设置级联加载的对象
     * @param fetch	需加载的对象
     * @param joinType	加载类型(JoinType)
     */
    public QueryCondition setFetchMode(String fetch, JoinType joinType){
    	FetchMode.put(fetch, joinType);
    	return this;
    }
	/**
	 * 批量设置级联加载的对象
	 * @param fetchMode
	 */
	public void setFetchMode(Map<String, JoinType> fetchMode) {
		FetchMode = fetchMode;
	}
    /**
	 * 获取级联加载的对象
	 */
	public Map<String, JoinType> getFetchMode() {
		return FetchMode;
	}
    
	/**
	 * 获取查询条件集合
	 * @return Map
	 */
	public Map<String, Object> getQueryParams() {
		if(queryParams == null){
			queryParams = new HashMap<String, Object>();
		}
		return queryParams;
	}
	/**
	 * 设置查询条件集合,Map的key值要遵循指定的规范,否则无效
	 * @param queryParams	查询条件集合
	 */
	public void setQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}
	
	/**
	 * 查询结果是否过滤重复
	 */
	public Boolean getResultTransformer() {
		return resultTransformer;
	}
	/**
	 * 设置查询结果是否过滤重复
	 * @param resultTransformer	是否过滤重复
	 */
	public void setResultTransformer(Boolean resultTransformer) {
		this.resultTransformer = resultTransformer;
	}
	/**
	 * 获取当前登录用户
	 */
	public LoginUser getLoginUser() {
		return loginUser;
	}
	/**
	 * 设置当前登录用户
	 */
	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}
	/**
	 * 获取OR查询条件集合
	 */
	public List<Condition[]> getQueryORParams() {
		if(queryORParams == null){
			queryORParams = new ArrayList<Condition[]>();
		}
		return queryORParams;
	}
}