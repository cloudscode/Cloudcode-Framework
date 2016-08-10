package com.cloudcode.framework.utils;

import java.util.List;
/**
 * 分页
 * @author owl
 *
 * @param <T>
 */
public class PaginationSupport<T> {
	/**
	 * 记录
	 */
	private List<T> rows;
	/**
	 * 总页数
	 */
	private int total;
	/**
	 * 当前页码
	 */
	private int page;
	/**
	 * 总记录数
	 */
	private int records;

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

}
