package com.bocweb.core.vo;


import java.io.Serializable;
/**
 * 分页参数对象
 * 包含排序字段和排序方式
 * @author tongpuxin
 */
public class Page implements Serializable{
	private static final long serialVersionUID = 2837480041430890963L;
	
	/**
	 * 构造函数
	 */
	public Page(){}
	
	/**
	 * 有参构造函数
	 * @param isPage	是否分页
	 */
	public Page(Boolean isPage){
		this.isPage = isPage;
	}
	
	/**当前页码*/
	private Integer pageNo  = null;
	/**起始记录数*/
	private Integer start = null;
	/**每页显示记录数*/
	private Integer limit = null;
	/**排序方式(desc || asc),多个用","分隔(与sort的每个字段对应)*/
	private String  dir;
	/**排序字段,多个字段用","分隔*/
	private String  sort;
	/**查询参数*/
	private Object  queryPara;
	/**总记录数*/
	private Long    totalCount;
	/**总页数*/
	private Long 	totalPage;
	/**是否分页*/
	private Boolean isPage = true;
	/**是否计算总记录数*/
	private Boolean isCountPage = true;
	
	public String toString(){
		return "Page [起始记录数=" + start + ", 每页显示记录数=" + limit + ", 排序字段=" + sort + ", 排序方式=" + dir + ", 是否分页=" + isPage + ", 是否计算总数=" + isCountPage + "]";
	}
	
	/**
	 * 获取起始记录数
	 */
	public Integer getStart() {
		if(start == null && pageNo != null && limit != null){
			//根据页码计算起始记录数
			start = (pageNo-1) * limit;
		}
		if(start == null)start = 0;
		return start;
	}
	/**
	 * 获取每页显示记录数
	 */
	public Integer getLimit() {
		return limit;
	}
	/**
	 * 获取排序方式
	 */
	public String getDir() {
		return dir;
	}
	/**
	 * 获取排序字段
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * 设置起始记录数
	 * @param start
	 */
	public void setStart(Integer start) {
		this.start = start;
	}
	/**
	 * 设置每页显示记录数
	 * @param limit
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	/**
	 * 设置排序方式
	 * @param dir
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	/**
	 * 设置排序字段
	 * @param sort
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * 获取查询参数
	 */
	public Object getQueryPara() {
		return queryPara;
	}
	/**
	 * 设置查询参数
	 * @param queryPara
	 */
	public void setQueryPara(Object queryPara) {
		this.queryPara = queryPara;
	}
	/**
	 * 获取总记录数
	 */
	public Long getTotalCount() {
		return totalCount;
	}
	/**
	 * 设置总记录数
	 * @param totalCount
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 获取是否分页
	 */
	public Boolean getIsPage() {
		return isPage;
	}
	/**
	 * 设置是否分页
	 * @param isPage
	 */
	public void setIsPage(Boolean isPage) {
		if(isPage == null)isPage = false;
		this.isPage = isPage;
		if(false == isPage){
			this.isCountPage = false;
		}
	}
	/**
	 * 获取是否计算总记录数
	 */
	public Boolean getIsCountPage() {
		return isCountPage;
	}
	/**
	 * 设置是否计算总记录数
	 * @param isCountPage
	 */
	public void setIsCountPage(Boolean isCountPage) {
		this.isCountPage = isCountPage;
	}
	/**
	 * 获取当前显示的页码
	 */
	public Integer getPageNo() {
		if(pageNo == null && start != null && limit != null){
			//根据起始记录数计算当前页码
			pageNo = (start/limit) + 1;
		}
		if(pageNo == null)pageNo = 1;
		return pageNo;
	}
	/**
	 * 设置当前显示的页码
	 * @param pageNo
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * 获取总页数
	 */
	public Long getTotalPage() {
		if(totalCount !=null && limit != null){
			totalPage = (totalCount + limit-1) / limit;
		}
		return totalPage;
	}
	/**
	 * 设置总页数
	 * @param totalPage
	 */
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

}
