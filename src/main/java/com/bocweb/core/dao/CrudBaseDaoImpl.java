package com.bocweb.core.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bocweb.core.entity.BaseEntity;
import com.bocweb.core.util.ReflectionUtils;
import com.bocweb.core.vo.Page;
import com.bocweb.core.vo.QueryCondition;


/**
 * 公共dao实现，采用泛型 只能通过继承的方式使用 eg. public class UserDaoImpl extends CrudBaseDaoSupport<User>
 * 
 * @author tongpuxin
 * @param <T>
 */
public abstract class CrudBaseDaoImpl<T extends BaseEntity> extends HibernateDaoImpl implements CrudBaseDao<T> {

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class.
	 */
	public CrudBaseDaoImpl() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		this.setIdFieldName(this.getIdFieldName(this.entityClass));
	}
	
	/**
	 * 修改对象某一属性
	 * @param propertyName	属性名称
	 * @param value			值
	 * @param id			ID
	 * @return	修改成功数量
	 */
	public <X> int update(final String propertyName, final Object value, final Serializable id){
		return this.update(entityClass, propertyName, value, id);
	}
	
	/**
	 * 批量修改对象属性
	 * @param <X>
	 * @param properties	属性，Map键值对<属性名,值>
	 * @param wheres		where条件,Map键值对<属性名,值>
	 * @return 修改成功数量
	 */
	public <X> int batchUpdate(final Map<String, Object> properties, final Map<String, Object> wheres){
		return this.batchUpdate(entityClass, properties, wheres);
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
	 * 按id删除对象.
	 * @param id	ID
	 */
	public void deleteById(final Serializable id){
		this.deleteById(entityClass, id);
	}
	/**
	 * 按id数组批量删除对象.
	 * @param ids	ID数组
	 */
	public void batchDelete(final Serializable[] ids){
		this.batchDelete(entityClass, ids);
	}
	/**
	 * 批量删除
	 * 根据指定属性名称和值
	 * @param propertyName	属性名
	 * @param value	属性值,可为数组或集合
	 */
	public void batchDelete(final String propertyName, final Serializable value){
		this.batchDelete(entityClass, propertyName, value);
	}
	/**
	 * 批量删除对象
	 * @param <X>
	 * @param wheres	where条件,Map键值对<属性名,值>
	 * @return 删除记录数
	 */
	public <X> int batchDelete(final Map<String, Object> wheres){
		return this.batchDelete(entityClass, wheres);
	}
	/**
	 * 按id获取对象.
	 * @param id	ID
	 */
	public T find(final Serializable id){
		return this.find(entityClass, id);
	}
	/**
	 * 按id获取对象.
	 * 同时级联加载指定关联对象
	 * @param id	ID
	 * @param fetch	需级联加载的关联对象
	 */
	public T find(final Serializable id, final String fetch[]){
		return this.find(entityClass, id, fetch);
	}
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public List<T> findBy(final String propertyName, final Object value){
		return this.findBy(entityClass, propertyName, value);
	}
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 * 仅能查询有且仅有一条记录的对象，否则会抛出异常
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public T findUniqueBy(final String propertyName, final Object value){
		return this.findUniqueBy(entityClass, propertyName, value);
	}
	/**
	 * 获取全部对象.
	 */
	public List<T> listAll(){
		return this.listAll(entityClass);
	}
	/**
	 * 获取全部对象,支持分页,支持排序.
	 * @param page	分页参数
	 */
	public List<T> listAll(Page page){
		return this.listAll(entityClass, page);
	}
	/**
	 * 动态组装条件查询，支持分页，支持排序
	 * @param condition	组装条件,包含分页参数和排序字段
	 */
	public List<T> queryByCondition(final QueryCondition condition){
		return this.queryByCondition(entityClass, condition);
	}
	/**
	 * 根据动态组装条件统计总数
	 * @param condition	组装条件
	 */
	public Long queryCountByCondition(final QueryCondition condition){
		return this.queryCountByCondition(entityClass, condition);
	}
}