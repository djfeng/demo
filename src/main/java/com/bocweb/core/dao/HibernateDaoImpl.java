package com.bocweb.core.dao;


import java.io.Serializable;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bocweb.core.util.sql.SqlColumnToBean;
import com.bocweb.core.vo.Page;
import com.bocweb.core.vo.QueryCondition;


@SuppressWarnings("unchecked")
@Repository
public class HibernateDaoImpl extends DaoImpl {
    
	@Autowired
    public void setSuperSessionFactory(SessionFactory sessionFactory){  
        super.setSessionFactory(sessionFactory);  
    }

	@Override
	public String getIdFieldName(Class<?> clazz){
		String idName = super.getIdFieldName();
		if(StringUtils.isBlank(idName)){
			idName = super.getIdFieldName(clazz);
			super.setIdFieldName(idName);
		}
		return idName;
	}
	
	/**
	 * 执行clear
	 */
	public void clear(){
		getSession().clear();
		logger.debug("clear");
	}
	
	/**
	 * 执行flush
	 */
	public void flush(){
		getSession().flush();
		logger.debug("flush");
	}
	
	/**
	 * 创建Blob
	 */
	public Blob createBlob(String str){
		if(str != null && !"".equals(str)){
			return getSession().getLobHelper().createBlob(str.getBytes());
		}
		return null;
	}
	
	/**
	 * 保存对象
	 */
	public void save(Object entity){
		Assert.notNull(entity, "entity不能为空");
		this.clear();
		getSession().save(entity);
		this.flush();
		logger.debug("save entity: {}", entity);
	}
	
	/**
	 * 保存或者修改对象
	 */
	public void saveOrUpdate(Object entity){
		Assert.notNull(entity, "entity不能为空");
		this.clear();
		getSession().saveOrUpdate(entity);
		this.flush();
		logger.debug("save entity: {}", entity);
	}
	
	/**
	 * 批量保存对象集合
	 */
	public <X> void batchSave(List<X> lists){
		Assert.notEmpty(lists, "list不能为空");
		if(lists != null && !lists.isEmpty()){
			int size = lists.size();
			for (int i = 0; i < size; i++) {
				X entity = lists.get(i);
				this.saveOrUpdate(entity);
			}
		}
	}
	
	/**
	 * 修改对象
	 */
	public void update(Object entity){
		Assert.notNull(entity, "entity不能为空");
		this.clear();
		getSession().update(entity);
		this.flush();
		logger.debug("update entity: {}", entity);
	}
	
	/**
	 * 修改对象某一属性
	 * @param id	propertyName
	 * @param id	value
	 * @param id	ID
	 */
	public <X> int update(Class<X> clazz, final String propertyName, final Object value, final Serializable id){
		StringBuilder hql = new StringBuilder("UPDATE "+clazz.getName()+" o ");
		hql.append("SET o.").append(propertyName).append("=").append(" ? ");
		hql.append("WHERE o.").append(this.getIdFieldName(clazz)).append(" = ?");
		return this.execute(hql.toString(), value, id);
	}
	
	/**
	 * 批量修改对象属性
	 * @param <X>
	 * @param clazz
	 * @param properties	属性，Map键值对<属性名,值>
	 * @param wheres		where条件,Map键值对<属性名,值>
	 * @return 修改成功数量
	 */
	public <X> int batchUpdate(Class<X> clazz, final Map<String, Object> properties, final Map<String, Object> wheres){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("UPDATE "+clazz.getName()+" o SET ");
		Set<String> keys = properties.keySet();
		int index = 0;
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(index >0)hql.append(",");
			hql.append("o.").append(key).append("=:").append(key).append(" ");
			params.put(key, properties.get(key));
			index ++;
		}
		keys = wheres.keySet();
		index = 0;
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String keyWhere = key.replace(".", "_");
			if(index >0)hql.append(" AND ");
			else hql.append(" WHERE ");
			hql.append("o.").append(key).append("=:where_").append(keyWhere).append(" ");
			params.put("where_"+keyWhere, wheres.get(key));
			index ++;
		}
		return this.execute(hql.toString(), params);
	}
	
	/**
	 * 删除对象
	 * 必须是从session中取出来的对象，若只有ID请使用deleteById方法
	 */
	public void delete(Object entity){
		Assert.notNull(entity, "entity不能为空");
		this.clear();
		getSession().delete(entity);
		this.flush();
		logger.debug("delete entity: {}", entity);
	}
	
	/**
	 * 按id删除对象.
	 * @param id	ID
	 */
	public <X> void deleteById(Class<X> clazz, final Serializable id){
		Assert.notNull(id, "id不能为空");
//		X x = find(clazz, id);
//		this.delete(x);
		StringBuilder hql = new StringBuilder("DELETE "+clazz.getName()+" o WHERE ");
		hql.append("o.").append(this.getIdFieldName(clazz)).append("=?");
		this.execute(hql.toString(), id);
	}
	
	/**
	 * 按id数组批量删除对象.
	 * @param ids	ID数组
	 */
	public <X> void batchDelete(Class<X> clazz, final Serializable[] ids){
		Assert.notEmpty(ids, "ids 不能为空");
		for (int i = 0; i < ids.length; i++) {
			this.deleteById(clazz, ids[i]);
		}
	}
	
	/**
	 * 批量删除
	 * 根据指定属性名称和值
	 * @param propertyName	属性名
	 * @param value	属性值,可为数组或集合
	 */
	public <X> void batchDelete(Class<X> clazz, final String propertyName, final Object value){
		Assert.notNull(propertyName, "propertyName不能为空");
		Assert.notNull(value, "value不能为空");
//		List<X> lists = this.findBy(clazz, propertyName, value);
//		if (!lists.isEmpty()) {
//			batchDelete(lists);
//		}
		StringBuilder hql = new StringBuilder("DELETE "+clazz.getName()+" o ");
		hql.append("WHERE o.").append(propertyName).append(" = ?");
		this.execute(hql.toString(), value);
		logger.debug("delete entity {},propertyName is {},value is {}", clazz.getSimpleName(), propertyName, value);
	}
	
	/**
	 * 批量删除对象
	 * @param <X>
	 * @param wheres	where条件,Map键值对<属性名,值>
	 * @return 删除记录数
	 */
	public <X> int batchDelete(Class<X> clazz, final Map<String, Object> wheres){
		StringBuilder hql = new StringBuilder("DELETE "+clazz.getName()+" o WHERE ");
		Set<String> keys = wheres.keySet();
		int index = 0;
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(index >0)hql.append(" AND ");
			hql.append("o.").append(key).append("=:").append(key);
			index ++;
		}
		return this.execute(hql.toString(), wheres);
	}
	
	/**
	 * 删除对象集合
	 */
	public <X> void batchDelete(List<X> lists){
		if(lists != null && !lists.isEmpty()){
			int size = lists.size();
			for (int i = 0; i < size; i++) {
				X entity = lists.get(i);
				if(entity != null)
					this.delete(entity);
			}
		}
	}
	
	/**
	 * 根据ID返回对象
	 */
	public <X> X find(Class<X> clazz, Serializable id){
		return (X) getSession().get(clazz, id);
	}
	
	/**
	 * 根据ID返回对象
	 * 同时级联加载指定对象
	 */
	public <X> X find(Class<X> clazz, Serializable id, String fetch[]){
		StringBuilder hql = new StringBuilder();
		hql.append("FROM ").append(clazz.getName()).append(" o ");
		if(fetch!=null && fetch.length>0){
			for (int i = 0; i < fetch.length; i++) {
				if(StringUtils.isNotBlank(fetch[i])){
					hql.append("LEFT JOIN FETCH o."+fetch[i]+" ");
				}
			}
		}
		hql.append("WHERE o.").append(this.getIdFieldName(clazz)).append(" = ?");
		List lists = this.query(hql.toString(), id);
		if(lists!=null && !lists.isEmpty())return (X) lists.iterator().next();
		return null;
	}
	
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public <X> List<X> findBy(Class<X> clazz, final String propertyName, final Object value){
		StringBuilder hql = new StringBuilder();
		hql.append("FROM ").append(clazz.getName()).append(" o ");
		hql.append("WHERE o.").append(propertyName).append(" = ?");
		return this.query(hql.toString(), value);
	}
	
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 * 仅能查询有且仅有一条记录的对象，否则会抛出异常
	 * @param propertyName	属性名
	 * @param value	值
	 */
	public <X> X findUniqueBy(Class<X> clazz, final String propertyName, final Object value){
		StringBuilder hql = new StringBuilder();
		hql.append("FROM ").append(clazz.getName()).append(" o ");
		hql.append("WHERE o.").append(propertyName).append(" = ?");
		return this.findUnique(hql.toString(), value);
	}
	
	/**
	 * 返回一个对象的所有集合，单表查询
	 */
	public <X> List<X> listAll(Class<X> clazz){
		StringBuilder hql = new StringBuilder();
		hql.append("FROM ").append(clazz.getName()).append(" o ");
		return this.query(hql.toString());
	}
	
	/**
	 * 返回一个对象的所有集合，单表查询，有分页
	 */
	public <X> List<X> listAll(Class<X> clazz, Page page){
		Map<String, Object> params = null;
		StringBuilder hql = new StringBuilder();
		hql.append("FROM ").append(clazz.getName()).append(" o ");
		hql.append(this.parseOrder("o", page));
		return this.query(hql.toString(), params, page);
	}
	
	/**
	 * 动态组装条件查询，支持分页，支持排序
	 * @param condition	组装条件,包含分页参数和排序字段
	 */
	public <X> List<X> queryByCondition(Class<X> clazz, final QueryCondition condition){
		Conjunction conjunction = this.parseCondition(condition);
		if(condition.getIsCountPage() != null && condition.getIsCountPage()){//查询总记录数
			Criteria totalCriteria = getSession().createCriteria(clazz);
			if(condition.getResultTransformer() != null && condition.getResultTransformer()){
				totalCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//去除重复
			}
			this.parseFetchMode(totalCriteria, condition);
			totalCriteria.add(conjunction);
			totalCriteria.setProjection(Projections.rowCount());
			Object count = totalCriteria.uniqueResult();
			Long totalCount = this.convertTotalCount(count);
			condition.setTotalCount(totalCount);
		}
		Criteria criteria = getSession().createCriteria(clazz);
		if(condition.getResultTransformer() != null && condition.getResultTransformer()){
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//去除重复
		}
		criteria.add(conjunction);
		this.parseFetchMode(criteria, condition);
		this.parseOrder(criteria, condition);
		Integer start = condition.getStart();
		Integer limit = condition.getLimit();
		if (start != null && limit != null && condition.getIsPage()!=null && condition.getIsPage()) {
			criteria.setFirstResult(start);
			criteria.setMaxResults(limit);
		} else {
			logger.warn("无法进行分页, 将查询所有数据 ");
		}
		return criteria.list();
	}
	
	/**
	 * 根据动态组装条件统计总数
	 * @param condition	组装条件
	 */
	public <X> Long queryCountByCondition(Class<X> clazz, final QueryCondition condition){
		Conjunction conjunction = this.parseCondition(condition);
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.add(conjunction);
		this.parseFetchMode(criteria, condition);
		criteria.setProjection(Projections.rowCount());
		Object count = criteria.uniqueResult();
		return this.convertTotalCount(count);
	}
	
	/**
	 * 根据查询HQL查询.
	 * @param hql
	 */
	public List query(final String hql){
		return createQuery(hql).list();
	}
	/**
	 * 根据查询HQL与参数列表查询.
	 * @param hql
	 * @param params	数量可变的参数,按顺序绑定.
	 */
	public List query(final String hql, final Object... params){
		return createQuery(hql, params).list();
	}
	/**
	 * 根据查询HQL与参数集合查询.
	 * @param hql
	 * @param params	命名参数,按名称绑定.
	 */
	public List query(final String hql, final Map<String, ?> params){
		return createQuery(hql, params).list();
	}
	/**
	 * 根据查询HQL与参数集合查询.
	 * @param hql
	 * @param params	命名参数,按名称绑定.
	 * @param page		分页参数
	 */
	public List query(final String hql, final Map<String, ?> params, final Page page){
		Query query = createQuery(hql, params);
		setPageParam(query, page);
		List list = query.list();
		if(page!=null && page.getIsPage()!=null && page.getIsPage() && 
				page.getIsCountPage() !=null && page.getIsCountPage()){
			int index = hql.toLowerCase().indexOf("from");
			String hqlCount = "SELECT count(*) " +hql.substring(index, hql.length());
			hqlCount = replaceSqlCount(hqlCount);
			query = createQuery(hqlCount, params);
			page.setTotalCount(convertTotalCount(query.uniqueResult()));
		}
		return list;
	}
	/**
	 * 根据统计HQL与参数集合统计总数.
	 * @param hqlCount	统计HQL语句
	 * @param params	命名参数,按名称绑定.
	 */
	public Long queryCount(final String hqlCount, final Map<String, ?> params){
		Object count = createQuery(hqlCount, params).uniqueResult();
		return convertTotalCount(count);
	}
	
	/**
	 * 根据查询SQL查询.
	 * @param sql
	 */
	public List queryBySQL(final String sql){
		return createSQLQuery(sql).list();
	}
	/**
	 * 根据查询SQL与参数列表查询.
	 * @param sql
	 * @param params	数量可变的参数,按顺序绑定.
	 */
	public List queryBySQL(final String sql, final Object... params){
		return createSQLQuery(sql, params).list();
	}
	/**
	 * 根据查询SQL与参数集合查询.
	 * @param sql
	 * @param params	命名参数,按名称绑定.
	 */
	public List queryBySQL(final String sql, final Map<String, ?> params){
		return createSQLQuery(sql, params).list();
	}
	/**
	 * 根据查询SQL与参数集合查询.
	 * @param sql
	 * @param params	命名参数,按名称绑定.
	 * @param page		分页参数
	 */
	public List queryBySQL(final String sql, final Map<String, ?> params, final Page page){
		return this.queryBySQL(sql, params, page, null);
	}
	/**
	 * 根据查询SQL与参数集合查询,并转换结果集.
	 * @param sql
	 * @param params	命名参数,按名称绑定.
	 * @param clazz		转换后对象
	 */
	public List queryBySQL(final String sql, final Map<String, ?> params, final Class clazz){
		return this.queryBySQL(sql, params, null, clazz);
	}
	/**
	 * 根据查询SQL与参数集合查询,并转换结果集.
	 * @param sql
	 * @param params	命名参数,按名称绑定.
	 * @param page		分页参数
	 * @param clazz		转换后对象
	 */
	public List queryBySQL(final String sql, final Map<String, ?> params, final Page page, final Class clazz){
		SQLQuery query = createSQLQuery(appendOrderBy(sql, page), params);
		/** 设置结果集转换器 */
		if(clazz != null)
			query.setResultTransformer(new SqlColumnToBean(clazz));
		if(page!=null && page.getIsPage()!=null && page.getIsPage()){
			query.setFirstResult(page.getStart());
			query.setMaxResults(page.getLimit());
		}
		List list = query.list();
		if(page!=null && page.getIsPage()!=null && page.getIsPage() 
				&& page.getIsCountPage()!=null && page.getIsCountPage()){
			String hqlCount = "SELECT count(1) FROM (" + sql+") o" ;
			hqlCount = replaceSqlCount(hqlCount);
			query = createSQLQuery(hqlCount, params);
			page.setTotalCount(convertTotalCount(query.uniqueResult()));
		}
		return list;
	}
	/**
	 * 根据统计SQL与参数集合统计总数.
	 * @param sqlCount	统计SQL语句
	 * @param params	命名参数,按名称绑定.
	 */
	public Long queryCountBySQL(final String sqlCount, final Map<String, ?> params){
		Object count = createSQLQuery(sqlCount, params).uniqueResult();
		return convertTotalCount(count);
	}

	/**
	 * 根据HQL查询唯一对象.
	 * @param hql
	 * @param params 数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... params) {
		return (X) createQuery(hql, params).uniqueResult();
	}

	/**
	 * 根据HQL查询唯一对象.
	 * @param hql
	 * @param params 命名参数,按名称绑定.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> params) {
		return (X) createQuery(hql, params).uniqueResult();
	}
	

/*****************************************  分割线	以下为执行SQL语句部分    ***********************************************/

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param hql
	 *            HQL
	 * @return 影响记录数.
	 */
	public int execute(String hql) {
		Query query = createQuery(hql);
		return query.executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param hql
	 *            HQL
	 * @param params
	 *            数量可变的参数,按顺序绑定.
	 * @return 影响记录数.
	 */
	public int execute(String hql, Object... params) {
		Query query = createQuery(hql, params);
		return query.executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param hql
	 *            HQL
	 * @param params
	 *            命名参数,按名称绑定.
	 * @return 影响记录数.
	 */
	public int execute(final String hql, final Map<String, Object> params) {
		Query query = createQuery(hql, params);
		return query.executeUpdate();
	}

	/**
	 * 执行SQL进行批量修改/删除操作.
	 * 
	 * @param sql
	 *            SQL
	 * @param params
	 *            数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int executeBySQL(final String sql, final Object... params) {
		SQLQuery query = createSQLQuery(sql, params);
		return query.executeUpdate();
	}

	/**
	 * 执行SQL进行批量修改/删除操作.
	 * 
	 * @param sql
	 *            SQL
	 * @param params
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int executeBySQL(final String sql, final Map<String, Object> params) {
		SQLQuery query = createSQLQuery(sql, params);
		return query.executeUpdate();
	}
	
} 
