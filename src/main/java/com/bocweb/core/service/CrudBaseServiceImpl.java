package com.bocweb.core.service;


import java.io.Serializable;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bocweb.core.dao.HibernateDaoImpl;
import com.bocweb.core.entity.BaseEntity;
import com.bocweb.core.util.ReflectionUtils;
import com.bocweb.core.vo.Page;
import com.bocweb.core.vo.QueryCondition;


/**
 * 公共Service实现
 * 只能通过继承的方式使用
 * @author	tongpuxin
 */
@Transactional
public abstract class CrudBaseServiceImpl<T extends BaseEntity> implements CrudBaseService<T> {

	@Autowired
	HibernateDaoImpl hibernateDaoImpl;
	
	protected Class<T> entityClass;
	
	/**
	 * 用于Service层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class.
	 */
	public CrudBaseServiceImpl() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}
	
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
	 * 创建Blob类型
	 * @param str
	 */
	public Blob createBlob(String str){
		return hibernateDaoImpl.createBlob(str);
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
	 * 删除对象
	 */
	public void delete(Object entity){
		hibernateDaoImpl.delete(entity);
	}

	/**
	 * 删除对象集合
	 */
	public <X> void batchDelete(List<X> lists){
		hibernateDaoImpl.batchDelete(lists);
	}
	
	
	
	/**
	 * 修改对象某一属性
	 * @param propertyName	属性名称
	 * @param value			值
	 * @param id			ID
	 * @return	修改成功数量
	 */
	public <X> int update(final String propertyName, final Object value, final Serializable id){
		return hibernateDaoImpl.update(entityClass, propertyName, value, id);
	}
	/**
	 * 批量修改对象某一属性
	 * @param propertyName	属性名称
	 * @param value			值
	 * @param ids			ID数组
	 * @return	修改成功数量
	 */
	public <X> int batchUpdate(final String propertyName, final Object value, final Serializable[] ids){
		int count = 0;
		if(ids != null && ids.length>0){
			for (int i = 0; i < ids.length; i++) {
				Serializable id = ids[i];
				if(id != null){
					count += this.update(propertyName, value, id);
				}
			}
		}
		return count;
	}
	/**
	 * 批量修改对象属性
	 * @param <X>
	 * @param properties	属性，Map键值对<属性名,值>
	 * @param wheres		where条件,Map键值对<属性名,值>
	 * @return 修改成功数量
	 */
	public <X> int batchUpdate(final Map<String, Object> properties, final Map<String, Object> wheres){
		return hibernateDaoImpl.batchUpdate(entityClass, properties, wheres);
	}
	/**
	 * 按id删除对象.
	 * @param id	ID
	 */
	public void deleteById(final Serializable id){
		hibernateDaoImpl.deleteById(entityClass, id);
	}
	/**
	 * 按id数组批量删除对象.
	 * @param ids	ID数组
	 */
	public void batchDelete(final Serializable[] ids){
		hibernateDaoImpl.batchDelete(entityClass, ids);
	}
	/**
	 * 批量删除
	 * 根据指定属性名称和值
	 * @param propertyName	属性名
	 * @param value	属性值,可为数组或集合
	 */
	public void batchDelete(final String propertyName, final Serializable value){
		hibernateDaoImpl.batchDelete(entityClass, propertyName, value);
	}
	/**
	 * 批量删除对象
	 * @param <X>
	 * @param wheres	where条件,Map键值对<属性名,值>
	 * @return 删除记录数
	 */
	public <X> int batchDelete(final Map<String, Object> wheres){
		return hibernateDaoImpl.batchDelete(entityClass, wheres);
	}
	/**
	 * 按id获取对象.
	 * @param id	ID
	 */
	public T find(final Serializable id){
		return hibernateDaoImpl.find(entityClass, id);
	}
	/**
	 * 按id获取对象.
	 * 同时级联加载指定关联对象
	 * @param id	ID
	 * @param fetch	需级联加载的关联对象
	 */
	public T find(final Serializable id, final String fetch[]){
		return hibernateDaoImpl.find(entityClass, id, fetch);
	}
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public List<T> findBy(final String propertyName, final Object value){
		return hibernateDaoImpl.findBy(entityClass, propertyName, value);
	}
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 * 仅能查询有且仅有一条记录的对象，否则会抛出异常
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public T findUniqueBy(final String propertyName, final Object value){
		return hibernateDaoImpl.findUniqueBy(entityClass, propertyName, value);
	}
	/**
	 * 获取全部对象.
	 */
	public List<T> listAll(){
		return hibernateDaoImpl.listAll(entityClass);
	}
	/**
	 * 获取全部对象,支持分页,支持排序.
	 * @param page	分页参数
	 */
	public List<T> listAll(Page page){
		return hibernateDaoImpl.listAll(entityClass, page);
	}
	/**
	 * 动态组装条件查询，支持分页，支持排序
	 * @param condition	组装条件,包含分页参数和排序字段
	 */
	public List<T> queryByCondition(final QueryCondition condition){
		return hibernateDaoImpl.queryByCondition(entityClass, condition);
	}
	/**
	 * 根据动态组装条件统计总数
	 * @param condition	组装条件
	 */
	public Long queryCountByCondition(final QueryCondition condition){
		return hibernateDaoImpl.queryCountByCondition(entityClass, condition);
	}
}
