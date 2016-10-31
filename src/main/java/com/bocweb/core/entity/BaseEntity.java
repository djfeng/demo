package com.bocweb.core.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * 统一定义Long型id(主键)的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	
}
