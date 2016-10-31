package com.bocweb.core.log;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法执行结束之后切入
 * @author tongpuxin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OperationAfter {
	
	/**
	 * 要执行的操作类型
	 */  
    public OperationType type();
    
    /**
     * 操作内容
     */
    public String description() default "";
    
}
