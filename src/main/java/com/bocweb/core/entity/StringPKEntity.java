package com.bocweb.core.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
/**
 * 统一定义String型id(主键)的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * @author tongpuxin
 */
@MappedSuperclass
public abstract class StringPKEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String oid;
	
	/**
	 * 获取主键值
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * 设置主键值
	 * @param oid	主键值
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
}
