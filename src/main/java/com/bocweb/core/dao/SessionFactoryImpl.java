package com.bocweb.core.dao;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bocweb.core.entity.LongPKEntity;
import com.bocweb.core.entity.RecordEntity;
import com.bocweb.core.entity.StringPKEntity;



public class SessionFactoryImpl {
	/**实体对应的主键名称*/
	private String idFieldName;
	
	protected static Logger logger = null;
	
	public SessionFactoryImpl(){
		logger = LoggerFactory.getLogger(getClass().getName());
	}
	
	@Autowired
    private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 需要开启事物，才能得到CurrentSession
	 * @return	Session
	 */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	
	/**
	 * 获取实体的名称
	 * @param clazz	实体Class
	 */
	protected String getEntityName(Class<?> clazz) {
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity != null && entity.name() != null && !"".equals(entity.name())) {
			return entity.name();
		}
		return clazz.getSimpleName();
	}
	
	/**
	 * 获取实体对应的数据库表名称
	 * @param clazz	实体Class
	 */
	protected String getTableName(Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if (table != null && table.name() != null && !"".equals(table.name())) {
			return table.name();
		}
		return clazz.getSimpleName();
	}
	
	/**
	 * 取得实体的主键名称.
	 * @param clazz	实体Class
	 */
	public String getIdFieldName(Class<?> clazz) {
		String keyField = null;
		Class<?> superclass = clazz.getSuperclass();
		if (superclass.getSimpleName().equals(RecordEntity.class.getSimpleName()) || 
				superclass.getSimpleName().equals(LongPKEntity.class.getSimpleName()) ||
				superclass.getSimpleName().equals(StringPKEntity.class.getSimpleName()) ) {
			keyField = "oid";
		} else {
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
				PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
				for (int i = 0; i < pds.length; i++) {
					PropertyDescriptor pd = pds[i];
					//在字段上注解Id
					try {
						Field field = clazz.getDeclaredField(pd.getName());
						if(field.isAnnotationPresent(Id.class)) {
							keyField = pd.getDisplayName();
							break;
						}
					} catch (Exception e) {
						logger.warn(clazz.getName() + "中没有Field: "+pd.getName());
					}
					//在get方法上注解Id
					Method getter = pd.getReadMethod();
				    if(getter != null && getter.isAnnotationPresent(Id.class)) {
				    	keyField = pd.getDisplayName();
			        	break;
				    }
				    //在set方法上注解Id
				    Method setter = pd.getReadMethod();
				    if(setter != null && setter.isAnnotationPresent(Id.class)) {
				    	keyField = pd.getDisplayName();
			        	break;
				    }
				}
			} catch (Exception e) {
				logger.error("无法获取实体的主键名.\n" + e.getMessage());
			}
		}
		if(StringUtils.isBlank(keyField)){
			return this.getIdFieldName(superclass);
		}
		return keyField;
	}
	public String getIdFieldName() {
		return idFieldName;
	}
	public void setIdFieldName(String idFieldName) {
		this.idFieldName = idFieldName;
	}
}
