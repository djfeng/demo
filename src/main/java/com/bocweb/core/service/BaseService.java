package com.bocweb.core.service;


import java.io.Serializable;
import java.util.List;

import com.bocweb.core.dao.BaseInterface;
import com.bocweb.core.vo.Page;
import com.bocweb.core.vo.QueryCondition;



public interface BaseService extends BaseInterface {
	/**
	 * 修改对象某一属性
	 * @param id	propertyName
	 * @param id	value
	 * @param id	ID
	 */
	public <X> void update(Class<X> clazz, final String propertyName, final Object value, final Serializable id);
	
	/**
	 * 按id删除对象.
	 * @param id	ID
	 */
	public <X> void deleteById(Class<X> clazz, final Serializable id);
	
	/**
	 * 按id数组批量删除对象.
	 * @param ids	ID数组
	 */
	public <X> void batchDelete(Class<X> clazz, final Serializable[] ids);
	
	/**
	 * 批量删除
	 * 根据指定属性名称和值
	 * @param propertyName	属性名
	 * @param value	属性值,可为数组或集合
	 */
	public <X> void batchDelete(Class<X> clazz, final String propertyName, final Object value);
	
	/**
	 * 根据ID返回对象
	 */
	public <X> X find(Class<X> clazz, Serializable id);
	
	/**
	 * 根据ID返回对象
	 * 同时级联加载指定对象
	 */
	public <X> X find(Class<X> clazz, Serializable id, String fetch[]);
	
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public <X> List<X> findBy(Class<X> clazz, final String propertyName, final Object value);
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 * 仅能查询有且仅有一条记录的对象，否则会抛出异常
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public <X> X findUniqueBy(Class<X> clazz, final String propertyName, final Object value);
	
	/**
	 * 返回一个对象的所有集合，单表查询
	 */
	public <X> List<X> listAll(Class<X> clazz);
	
	/**
	 * 返回一个对象的所有集合，单表查询，有分页
	 */
	public <X> List<X> listAll(Class<X> clazz, Page page);
	
	/**
	 * 动态组装条件查询，支持分页，支持排序
	 * @param condition	组装条件,包含分页参数和排序字段
	 */
	public <X> List<X> queryByCondition(Class<X> clazz, final QueryCondition condition);
	
	/**
	 * 根据动态组装条件统计总数
	 * @param condition	组装条件
	 */
	public <X> Long queryCountByCondition(Class<X> clazz, final QueryCondition condition);
}
