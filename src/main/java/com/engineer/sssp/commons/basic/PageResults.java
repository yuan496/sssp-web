package com.engineer.sssp.commons.basic;

import java.util.List;

/**
 * 分页对象
 * @author fangzhibin 2015年4月1日 上午9:43:50
 * @version V1.0
 * @param <T>
 * @modify: {原因} by fangzhibin 2015年4月1日 上午9:43:50
 */
public class PageResults<T> {
	
	/**
	 * 下一页数
	 */
	private int pageNextNo;
	/**
	 * 当前页数
	 */
	private int currentPage;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 总页数
	 */
	private int pageCount;
	/**
	 * 数据集
	 */
	private List<T> results;
	
	public int getPageNextNo() {
		if (pageNextNo <= 0) {
			return 1;
		} else {
			return pageNextNo > pageCount?pageCount:pageNextNo;
		}
	}
	
	public void setPageNextNo(int pageNextNo) {
		this.pageNextNo = pageNextNo;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSize() {
		return pageSize <= 0?20:pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public List<T> getResults() {
		return results;
	}
	
	public void setResults(List<T> results) {
		this.results = results;
	}
	
	/**
	 * 重置下一页数
	 * @author fangzhibin 2015年4月1日 上午9:51:42
	 * @modify: {原因} by fangzhibin 2015年4月1日 上午9:51:42
	 */
	public void resetPageNo() {
		pageNextNo = currentPage + 1;
		pageCount = totalCount % pageSize == 0?totalCount / pageSize:totalCount / pageSize + 1;
	}
}
