package com.coolpeng.framework.db;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {

	private int totalCount;
	private int pageSize;
	private int pageNumber;
	private int pageCount;
	private List<T> pageData;

	public PageResult(int totalCount, int pageSize, int pageNumber, List<T> pageData) {
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.pageData = pageData;
		this.pageCount = totalCount % pageSize == 0 ? (totalCount / pageSize) : ((totalCount / pageSize) + 1);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<T> getPageData() {
		if (pageData==null){
			return new ArrayList<>();
		}
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	

}
