package com.bocweb.core.service;


import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bocweb.core.dao.HibernateDaoImpl;
import com.bocweb.core.vo.Page;
import com.bocweb.core.vo.QueryCondition;



@Service("baseService")
public class BaseServiceImpl implements BaseService{
	
	@Autowired
	HibernateDaoImpl hibernateDaoImpl;
	
	/**
	 * 执行clear
	 */
	public void clear(){
		hibernateDaoImpl.clear();
	}
	
	/**
	 * 执行flush
	 */
	public void flush(){
		hibernateDaoImpl.flush();
	}
	
	/**
	 * 保存对象
	 */
	public void save(Object entity){
		hibernateDaoImpl.save(entity);
	}
	
	/**
	 * 保存或者修改对象
	 */
	public void saveOrUpdate(Object entity){
		hibernateDaoImpl.saveOrUpdate(entity);
	}
	
	/**
	 * 批量保存对象集合
	 */
	public <X> void batchSave(List<X> lists){
		hibernateDaoImpl.batchSave(lists);
	}
	
	/**
	 * 修改对象
	 */
	public void update(Object entity){
		hibernateDaoImpl.update(entity);
	}
	
	/**
	 * 修改对象某一属性
	 * @param id	propertyName
	 * @param id	value
	 * @param id	ID
	 */
	public <X> void update(Class<X> clazz, final String propertyName, final Object value, final Serializable id){
		hibernateDaoImpl.update(clazz, propertyName, value, id);
	}
	
	/**
	 * 删除对象
	 */
	public void delete(Object entity){
		hibernateDaoImpl.delete(entity);
	}
	
	/**
	 * 按id删除对象.
	 * @param id	ID
	 */
	public <X> void deleteById(Class<X> clazz, final Serializable id){
		hibernateDaoImpl.deleteById(clazz, id);
	}
	
	/**
	 * 按id数组批量删除对象.
	 * @param ids	ID数组
	 */
	public <X> void batchDelete(Class<X> clazz, final Serializable[] ids){
		hibernateDaoImpl.batchDelete(clazz, ids);
	}
	
	/**
	 * 批量删除
	 * 根据指定属性名称和值
	 * @param propertyName	属性名
	 * @param value	属性值,可为数组或集合
	 */
	public <X> void batchDelete(Class<X> clazz, final String propertyName, final Object value){
		hibernateDaoImpl.batchDelete(clazz, propertyName, value);
	}
	
	/**
	 * 删除对象集合
	 */
	public <X> void batchDelete(List<X> lists){
		hibernateDaoImpl.batchDelete(lists);
	}
	
	/**
	 * 根据ID返回对象
	 */
	public <X> X find(Class<X> clazz, Serializable id){
		return hibernateDaoImpl.find(clazz, id);
	}
	
	/**
	 * 根据ID返回对象
	 * 同时级联加载指定对象
	 */
	public <X> X find(Class<X> clazz, Serializable id, String fetch[]){
		return hibernateDaoImpl.find(clazz, id, fetch);
	}
	
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public <X> List<X> findBy(Class<X> clazz, final String propertyName, final Object value){
		return hibernateDaoImpl.findBy(clazz, propertyName, value);
	}
	
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 * 仅能查询有且仅有一条记录的对象，否则会抛出异常
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public <X> X findUniqueBy(Class<X> clazz, final String propertyName, final Object value){
		return hibernateDaoImpl.findUniqueBy(clazz, propertyName, value);
	}
	
	/**
	 * 返回一个对象的所有集合，单表查询
	 */
	public <X> List<X> listAll(Class<X> clazz){
		return hibernateDaoImpl.listAll(clazz);
	}
	
	/**
	 * 返回一个对象的所有集合，单表查询，有分页
	 */
	public <X> List<X> listAll(Class<X> clazz, Page page){
		return hibernateDaoImpl.listAll(clazz, page);
	}
	
	/**
	 * 动态组装条件查询，支持分页，支持排序
	 * @param condition	组装条件,包含分页参数和排序字段
	 */
	public <X> List<X> queryByCondition(Class<X> clazz, final QueryCondition condition){
		return hibernateDaoImpl.queryByCondition(clazz, condition);
	}
	
	/**
	 * 根据动态组装条件统计总数
	 * @param condition	组装条件
	 */
	public <X> Long queryCountByCondition(Class<X> clazz, final QueryCondition condition){
		return hibernateDaoImpl.queryCountByCondition(clazz, condition);
	}
}
