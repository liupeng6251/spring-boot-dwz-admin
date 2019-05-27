package org.qvit.hybrid.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 配合前端分页组件使用
 * 
 * @author tianyale 2016年9月2日
 *
 * @param <T>
 */
public class PageVO<T> implements Serializable {

	private static final long serialVersionUID = -8047830023754248286L;

	private static final int DEFAULT_PAGE_SIZE = 20;
	
	private int pageSize = DEFAULT_PAGE_SIZE;

	private Integer pageNo = 1;

	private Long totalCount;

	private List<T> results = new ArrayList<>();

	/**
	 * results中最后一个元素的id，方便处理分页性能问题
	 */
	private Long lastId;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}
}
