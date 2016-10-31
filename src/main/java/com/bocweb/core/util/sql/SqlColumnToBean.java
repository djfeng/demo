package com.bocweb.core.util.sql;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

import com.bocweb.core.util.date.DateFormatUtil;

/**
 * SQL查询结果集转换器
 * 用法(*代表包含SQL中所有字段的类)：
 *	Query query = s.createSQLQuery(SQL);
 *	query.setResultTransformer(new SqlColumnToBean(*.class));//设置结果集转换器
 *	return query.list();
 * 
 * @author tongpuxin
 */
public class SqlColumnToBean implements ResultTransformer {
	
	private static final long serialVersionUID = 1L;
	private final Class<?> resultClass;
	private Setter[] setters;
	private PropertyAccessor propertyAccessor;
	private HashMap<String, String> fieldTypes = new HashMap<String, String>();

	public SqlColumnToBean(Class<?> resultClass) {
		if (resultClass == null)
			throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClass = resultClass;
		propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] {
				PropertyAccessorFactory.getPropertyAccessor(resultClass, null),
				PropertyAccessorFactory.getPropertyAccessor("field") });
	}

	/**
	 * 结果转换时，HIBERNATE调用此方法
	 * tuple值
	 * aliases属性名
	 */
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result = null;
		try {
			if (setters == null) {
				/** 首先初始化，取得目标POJO类的所有SETTER方法 */
				setters = new Setter[aliases.length];
				for (int i = 0; i < aliases.length; i++) {
					String alias = aliases[i];
					setters[i] = getSetterByColumnName(resultClass, alias);
				}
			}
			result = resultClass.newInstance();
			/** 这里使用SETTER方法填充POJO对象 */
			for (int i = 0; i < aliases.length; i++) {
				if (setters[i] != null) {
					String proName = aliases[i].replaceAll("_", "").toLowerCase();
					this.setFieldValue(result, setters[i], proName, tuple[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/*private Setter getSetterByColumnName(String alias) {
		if (alias == null) return null;
		*//** 取得POJO所有属性名 *//*
		Field[] fields = resultClass.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			throw new RuntimeException("实体" + resultClass.getName() + "不含任何属性");
		}
		*//** 把字段名中所有的下杠去除 *//*
		String proName = alias.replaceAll("_", "").toLowerCase();
		for (Field field : fields) {
			if (field.getName().toLowerCase().equals(proName)) {
				*//** 去除下杠的字段名如果和属性名对得上，就取这个SETTER方法 *//*
				fieldTypes.put(proName, field.getType().getSimpleName());
				return propertyAccessor.getSetter(resultClass, field.getName());
			}
		}
		System.out.println(DateFormatUtil.getStringDateYMDHMS()+" 警告 [SqlColumnToBean] getSetterByColumnName " +
				"找不到数据库字段  " + alias + " 对应的POJO属性或其getter方法，比如数据库字段为USER_ID或USERID，那么JAVA属性应为userId");
		return null;
		throw new RuntimeException("找不到数据库字段 ：" + alias + " 对应的POJO属性或其getter方法，" +
				"比如数据库字段为USER_ID或USERID，那么JAVA属性应为userId");
	}*/
	private Setter getSetterByColumnName(Class<?> clazz, String alias) {
		if (alias == null){
			return null;
		}
		if(clazz.getSimpleName().equals("Object")){
			this.log(alias);
			return null;
		}
		/** 取得POJO所有属性名 */
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			/** 把字段名中所有的下杠去除 */
			String proName = alias.replaceAll("_", "").toLowerCase();
			for (Field field : fields) {
				if (field.getName().toLowerCase().equals(proName)) {
					/** 去除下杠的字段名如果和属性名对得上，就取这个SETTER方法 */
					fieldTypes.put(proName, field.getType().getSimpleName());
					return propertyAccessor.getSetter(clazz, field.getName());
				}
			}
		}
		return getSetterByColumnName(clazz.getSuperclass(), alias);
	}

	@SuppressWarnings("unchecked")
	public List transformList(List collection) {
		return collection;
	}
	
	private void setFieldValue(Object result, Setter setter, String proName, Object value) throws Exception{
		if(value==null)return;
		String typeValue = fieldTypes.get(proName);
		if ("Boolean".equals(typeValue)) {
			Boolean b = null;
			if("1".equals(value.toString()) || "true".equals(value.toString())){
				b = true;
			}else if("0".equals(value.toString()) || "false".equals(value.toString())){
				b = false;
			}else{
				System.out.println(DateFormatUtil.getStringDateYMDHMS()+" 警告 [SqlColumnToBean] setFieldValue Boolean类型值转换错误.");
			}
			setter.set(result, b, null);
		} else if ("String".equals(typeValue)) {
			String content = null;
			if(value instanceof Clob){
				Clob clob = (Clob)value;
				if(clob != null)content = clob.getSubString(1, (int)clob.length());
			}else{
				content = value.toString();
			}
			setter.set(result, content, null);
		} else if ("Integer".equals(typeValue)) {
			setter.set(result, Integer.parseInt(value.toString()), null);
		} else if ("Long".equals(typeValue)) {
			setter.set(result, Long.parseLong(value.toString()), null);
		} else if ("Float".equals(typeValue)) {
			setter.set(result, (Float)value, null);
		} else if ("Double".equals(typeValue)) {
			setter.set(result, (Double)value, null);
		} else if ("BigDecimal".equals(typeValue)) {
			setter.set(result, new BigDecimal(value.toString()), null);
		} else if ("Byte".equals(typeValue)) {
			setter.set(result, (Byte)value, null);
		} else if ("Calendar".equals(typeValue)) {
			setter.set(result, (Calendar)value, null);
		} else if ("Character".equals(typeValue)) {
			setter.set(result, (Character)value, null);
		} else if ("Timestamp".equals(typeValue)) {
			setter.set(result, (Timestamp)value, null);
		} else if ("Date".equals(typeValue)) {
			setter.set(result, (Date)value, null);
		} else if ("Short".equals(typeValue)) {
			setter.set(result, (Short)value, null);
		}
	}
	
	private void log(String alias){
		System.out.println(DateFormatUtil.getStringDateYMDHMS()+" 警告 [SqlColumnToBean] getSetterByColumnName " +
				"找不到数据库字段  " + alias + " 对应的POJO属性或其getter方法，比如数据库字段为USER_ID或USERID，那么JAVA属性应为userId");
	}
}
