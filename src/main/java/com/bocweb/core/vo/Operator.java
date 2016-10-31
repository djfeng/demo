package com.bocweb.core.vo;


/**
 * 数据库比较符
 */
public enum Operator {
	/**
	 * 等于
	 */
    EQ, 
    /**
     * 不等于
     */
    NEQ, 
    /**
     * like(eg: "%" + value + "%")
     */
    LIKE, 
    /**
     * like(eg: value + "%")
     */
    LLIKE, 
    /**
     * like(eg: "%" + value)
     */
    RLIKE, 
    /**
     * 大于
     */
    GT,  
    /**
     * 大于等于
     */
    GTE, 
    /**
     * 小于
     */
    LT,
    /**
     * 小于等于
     */
    LTE,
    /**
     * null
     */
    NULL, 
    /**
     * not null
     */
    NNULL, 
    /**
     * not like
     */
    NLIKE, 
    /**
     * in
     */
    IN
}