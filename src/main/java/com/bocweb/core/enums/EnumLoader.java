package com.bocweb.core.enums;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bocweb.core.log.OperationType;



/**
 * 枚举装载器
 * (装载时会缓存枚举类中的所有值，但当不同枚举类中有相同值时会覆盖，故使用时尽量避免使用相同的枚举值)
 * 
 * @author tongpuxin
 */
public class EnumLoader {
	
	/**
	 * 缓存所有的枚举值
	 * <枚举类名称,对应的枚举类>或<枚举值,对应的枚举类>
	 */
	private static Map<String, Class<?>> enums = new HashMap<String, Class<?>>();
	
	static {
		init();
	}
	
	private static void init(){
		init(SexFlag.class);
		init(StatusFlag.class);
		init(OperationType.class);
	}
	
	/**
	 * 根据枚举类Class缓存枚举值
	 * @param clazz	枚举类Class
	 */
	public static void cacheValue(Class<?> clazz){
		Object[] c = clazz.getEnumConstants();
		for (int i = 0; i < c.length; i++) {
			enums.put(c[i].toString(), clazz);
		}
	}
	
	/**
	 * 装载枚举类
	 * @param clazz	枚举类Class
	 */
	public static void init(Class<?> clazz){
		enums.put(clazz.getSimpleName(), clazz);
		cacheValue(clazz);
	}
	
	/**
	 * 根据枚举类名称获取枚举类
	 * @param clazzName 枚举类名称
	 * @return	枚举类Class
	 */
	public static Class<?> get(String clazzName){
		return enums.get(clazzName);
	}

	/**
	 * 根据枚举值获取枚举类
	 * @param value	枚举值
	 * @return	枚举类Class
	 */
	public static Class<?> getClass(Object value){
		return enums.get(value);
	}
	
	/**
	 * 根据枚举属性名称和枚举值获取枚举类
	 * @param propertyName	枚举属性名称
	 * @param value	枚举值
	 * @return	枚举类Class
	 */
	public static Class<?> getClass(String propertyName, Object value){
		Class<?> propertyClass = EnumLoader.get(StringUtils.capitalize(propertyName));//此写法只支持枚举属性名与类名一致
		if(propertyClass == null){
			propertyClass = getClass(value);
		}
		return propertyClass;
	}
	
	/**
	 * 根据枚举属性名称和枚举值获取枚举型的值
	 * @param propertyName	枚举属性名称
	 * @param value	枚举值
	 * @return	Enum
	 */
	public static Object getValue(String propertyName, Object value){
		Object v = value;
		try {
			if(!(value instanceof Enum)){//非枚举值
				Class<?> propertyClass = getClass(propertyName, value);
				//获取枚举类的valueOf()方法
			    Method valueOfMethod = propertyClass.getMethod("valueOf", String.class);
			    //调用枚举对象上的valueOf()方法
			    v = valueOfMethod.invoke(propertyClass, value);
				/*Method[] methods = propertyClass.getDeclaredMethods();
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if(method.getName().startsWith("valueOf")){
						value = method.invoke(propertyClass, value);
						break;
					}
				}*/
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return v;
	}
}
