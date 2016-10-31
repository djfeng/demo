package com.bocweb.core.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义Long型id(主键)的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 基类统一定义所属单位(该字段不能为空)、创建人信息、最后一次修改人信息.
 */
@MappedSuperclass
public abstract class RecordEntity extends BaseEntity {
	
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long 	oid;

	/**创建人ID*/
	private Long    createUserId;
	/**创建人姓名*/
	private String  createUserName;
	/**创建时间*/
	private String  createDate;
	/**最后修改人ID*/
	private Long    lastModifiedUserId;
	/**最后修改人姓名*/
	private String  lastModifiedUserName;
	/**最后修改时间*/
	private String  lastModifiedDate;
	
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
	
	/**
	 * 获取创建人ID
	 */
	public Long getCreateUserId() {
		return createUserId;
	}
	
	/**
	 * 设置创建人ID
	 * @param createUserId	创建人ID
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	/**
	 * 获取创建人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置创建人姓名
	 * @param createUserName	创建人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取创建时间
	 */
	public String getCreateDate() {
		return createDate;
	}
	
	/**
	 * 设置创建时间
	 * @param createDate	创建时间
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 获取最后一次修改人ID
	 */
	public Long getLastModifiedUserId() {
		return lastModifiedUserId;
	}
	
	/**
	 * 设置最后一次修改人ID
	 * @param lastModifiedUserId	最后一次修改人ID
	 */
	public void setLastModifiedUserId(Long lastModifiedUserId) {
		this.lastModifiedUserId = lastModifiedUserId;
	}
	
	/**
	 * 获取最后一次修改人姓名
	 */
	public String getLastModifiedUserName() {
		return lastModifiedUserName;
	}
	
	/**
	 * 设置最后一次修改人姓名
	 * @param lastModifiedUserName	最后一次修改人姓名
	 */
	public void setLastModifiedUserName(String lastModifiedUserName) {
		this.lastModifiedUserName = lastModifiedUserName;
	}
	
	/**
	 * 获取最后一次修改时间
	 */
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	/**
	 * 设置最后一次修改时间
	 * @param lastModifiedDate	最后一次修改时间
	 */
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
