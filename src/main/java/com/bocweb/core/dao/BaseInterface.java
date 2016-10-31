package com.bocweb.core.dao;


import java.util.List;

public interface BaseInterface {

	/**
	 * 执行clear
	 */
	public void clear();
	
	/**
	 * 执行flush
	 */
	public void flush();
	
	/**
	 * 保存对象
	 */
	public void save(Object entity);
	
	/**
	 * 保存或者修改对象
	 */
	public void saveOrUpdate(Object entity);
	
	/**
	 * 批量保存对象集合
	 */
	public <X> void batchSave(List<X> lists);
	
	/**
	 * 修改对象
	 */
	public void update(Object entity);
	
	/**
	 * 删除对象
	 */
	public void delete(Object entity);

	/**
	 * 删除对象集合
	 */
	public <X> void batchDelete(List<X> lists);
}
