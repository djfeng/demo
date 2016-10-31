package com.bocweb.core.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
/**
 * 统一定义Long型id(主键)的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * @author tongpuxin
 */
@MappedSuperclass
public abstract class LongPKEntity extends BaseEntity {
	
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long 	oid;
	
	/**
	 * 获取主键值
	 */
	public Long getOid() {
		return oid;
	}
	/**
	 * 设置主键值
	 * @param oid	主键值
	 */
	public void setOid(Long oid) {
		this.oid = oid;
	}
}
