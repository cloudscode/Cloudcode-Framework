package com.cloudcode.framework.utils;

/**
 * 查询条件
 * 
 * @author owl
 * 
 */
public class PageRange {
	/**
	 * 当前页码
	 */
	private int page;
	/**
	 * 第几条数据开始
	 */
	private int start;
	/**
	 * 每页中现实的记录行数
	 */
	private int rows;

	public PageRange() {

	}

	public PageRange(int page, int start, int rows) {
		this.page = page;
		this.start = start;
		this.rows = rows;
	}

	public int getEnd() {
		return rows + getStart();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStart() {
		this.start = (page - 1) * rows; // 开始记录数
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
